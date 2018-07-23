package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.connection.SocketInfo;
import com.app.hos.server.connection.TcpConnection;
import com.app.hos.server.event.TcpEvent;
import com.app.hos.server.event.TcpEventFactory;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.messaging.TcpMessageMapper;
import com.app.hos.server.serializer.ByteArrayDeserializer;

public class TcpServer implements Server, TcpServerListener, Runnable {

	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private final int portNumber;
	
	private volatile ServerSocket serverSocket;
	
	private final Object monitor = new Object();
	
	private volatile ExecutorService connectionExecutor;
	
	private volatile boolean active;

	private volatile Serializer<?> serializer;
	
	private volatile Deserializer<?> deserializer  = new ByteArrayDeserializer();
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private ConnectionFactory connectionFactory;
	
	private TcpMessageMapper mapper;
	
	private TcpListener listener;
	
	public TcpServer (int portNumber) {
		this.portNumber = portNumber;
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		if (this.connectionFactory == null) {
			logger.info(this + " No connection factory bound to server; will not read; exiting...");
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
						this.connectionFactory.addConnection(tcpConnection);
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
		this.connectionFactory.closeConnections();
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
		connection.setConnectionFactory(this.connectionFactory);
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
		TcpEvent event = TcpEventFactory.OPEN_CONNECTION.create(socketInfo);
		publishServerEvent(event);
	}
	
	private void publishServerExceptionEvent(Throwable cause) {
		TcpEvent event = TcpEventFactory.SERVER_EXCEPTION.create(this,cause);
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
	public void setConnnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
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

}
