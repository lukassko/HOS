package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.TcpListener;
import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.SocketInfo;
import com.app.hos.server.connection.TcpConnection;
import com.app.hos.server.event.TcpEvent;
import com.app.hos.server.event.TcpEventTypeFactory;
import com.app.hos.server.event.source.TcpConnectionEventSource;
import com.app.hos.server.event.source.TcpServerEventSource;
import com.app.hos.server.messaging.TcpMessageMapper;
import com.app.hos.server.serializer.ByteArrayDeserializer;

public class TcpServer implements Server, ConnectionContainer, TcpServerListener, Runnable {

	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private final int portNumber;
	
	private volatile ServerSocket serverSocket;
	
	private final Map<String, Connection> connections = new ConcurrentHashMap<String, Connection>();
	
	private final Object monitor = new Object();
	
	private volatile ExecutorService connectionExecutor;
	
	private volatile boolean active;

	private volatile Serializer<?> serializer;
	
	private volatile Deserializer<?> deserializer  = new ByteArrayDeserializer();
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private TcpMessageMapper mapper;
	
	private TcpListener listener;
	
	public TcpServer (int portNumber) {
		this.portNumber = portNumber;
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		if (this.listener == null) {
			logger.info(this + " No listener bound to server connection factory; will not read; exiting...");
			return;
		}
		try {
			ServerSocket theServerSocket = new ServerSocket(portNumber);
			while (true) {
				Socket socket;
				if (theServerSocket == null) {
					throw new IOException("Server socket close");
				} else {
					socket = theServerSocket.accept();
				}
				if (!isActive()) {
					socket.close();
				} else {
					try {
						TcpConnection tcpConnection = createConnection(socket);
						initializeConnection(tcpConnection);
						publishOpenConnectionEvent(tcpConnection.getSocketInfo());
					} catch (SocketException e) {
						this.logger.log(Level.SEVERE, "Failed to create and configure a TcpConnection for the new socket: "
								+ socket.getInetAddress().getHostAddress() + ":" + socket.getPort(), e);
						socket.close();
					}
				}
			}
		} catch (IOException e) {
			publishServerExceptionEvent(e);
		} finally {
			this.stop();
		}
	}

	@Override
	public Connection getConnection(String connectionId) {
		return this.connections.get(connectionId);
	}
	
	public boolean isActive() {
		return active;
	}

	public void start() {
		if (!this.active) {
			synchronized (monitor) {
				this.active = true;
				getTaskExecutor().execute(this);
			}
		}
	}
	
	public void stop() {
		if (this.serverSocket == null) {
			return;
		}
		try {
			this.serverSocket.close();
		} catch (IOException e) {
		}
		this.serverSocket = null;
		this.active = false;
		closeActiveConnections();
	}
	
	private void closeActiveConnections() {
		synchronized(this.connections) {
			Iterator<Entry<String,Connection>> iterator= this.connections.entrySet().iterator();
			while(iterator.hasNext()) {
				Connection connection = iterator.next().getValue();
				connection.close();
				iterator.remove();
			}
		}
		synchronized(this.monitor) {
			connectionExecutor.shutdown();
			try {
				if (!connectionExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
					connectionExecutor.shutdownNow();
					connectionExecutor.awaitTermination(10, TimeUnit.SECONDS);
				}
			} catch (InterruptedException e) {
				connectionExecutor.shutdownNow();
			} finally {
				this.connectionExecutor = null;
			}
		}
	}
	
	private TcpConnection createConnection(Socket socket) throws SocketException {
		return new TcpConnection.SocketAttributes().socket(socket).build();
	}
	
	private void initializeConnection(TcpConnection connection) {
		connection.setListener(this.listener);
		connection.setMapper(this.mapper);
		connection.setSerializer(this.serializer);
		connection.setDeserializer(this.deserializer);
		connection.setApplicationEventPublisher(applicationEventPublisher);
		connection.setTcpConnectionContainer(this);
		addNewConnection(connection);
	}

	private Executor getTaskExecutor() {
		if (!this.active) {
			throw new RuntimeException("Connection Factory not started");
		}
		if (this.connectionExecutor == null) {
			synchronized(this.monitor) {
				if (this.connectionExecutor == null) {
					this.connectionExecutor = Executors.newCachedThreadPool();
				}
			}
		}
		return this.connectionExecutor;
	}
	
	private void publishOpenConnectionEvent(SocketInfo socketInfo) {
		TcpConnectionEventSource eventSource = new TcpConnectionEventSource(socketInfo);
		TcpEvent event = TcpEventTypeFactory.OPEN_CONNECTION.create(eventSource);
		publishServerEvent(event);
	}
	
	private void publishServerExceptionEvent(Throwable cause) {
		TcpServerEventSource eventSource = new TcpServerEventSource(this, cause);
		TcpEvent event = TcpEventTypeFactory.SERVER_EXCEPTION.create(eventSource);
		publishServerEvent(event);
	}
	
	private void publishServerEvent(TcpEvent event) {
		if (this.applicationEventPublisher == null) {
			this.logger.warning("No ApplicationEventPublisher to publish event " + event.toString());
		} else {
			this.applicationEventPublisher.publishEvent(event);
		}
	}

	@Override
	public void setMapper(TcpMessageMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void setSerializer(Serializer<?> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void setDeserializer(Deserializer<?> deserializer) {
		this.deserializer = deserializer;
	}

	@Override
	public void setListener(TcpListener listener) {
		this.listener = listener;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public int getPort() {
		return this.serverSocket.getLocalPort();
	}

	@Override
	public SocketAddress getSocketAddress() {
		return this.serverSocket.getLocalSocketAddress();
	}

	@Override
	public boolean isListening() {
		return this.isActive();
	}

	@Override
	public String toString() {
		return "TcpServer [portNumber=" + this.portNumber + ", serverSocket=" + this.serverSocket.getInetAddress() + "]";
	}

	@Override
	public void addNewConnection(Connection connection) {
		synchronized(this.connections) {
			if (!this.active) {
				connection.close();
				return;
			}
			this.connections.put(connection.getConnectionId(), connection);
		}
	}

	@Override
	public void removeDeadConnection(Connection connection) {
		synchronized(this.connections) {
			this.connections.remove(connection.getConnectionId());
		}
	}
	
}
