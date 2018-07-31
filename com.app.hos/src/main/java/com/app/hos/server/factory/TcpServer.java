package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
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
	
	private volatile boolean active;

	private volatile Serializer<?> serializer;
	
	private volatile Deserializer<?> deserializer  = new ByteArrayDeserializer();
	
	private volatile ApplicationEventPublisher applicationEventPublisher;
	
	private volatile ConnectionManager connectionManager;
	
	private volatile TcpMessageMapper mapper;
	
	private volatile TcpListener listener;
	
	private volatile SocketFactory socketFactory = new TcpSocketFactory();
	
	private volatile ThreadsExecutor threadsExecutor = new TcpThreadsExecutor();
	
	public TcpServer (int portNumber) {
		this.portNumber = portNumber;
	}

	@Override
	public void run() {
		if (this.connectionManager == null) {
			logger.info(this + " No connection manager bound to the server; will not read; exiting...");
			return;
		}
		try {
			this.serverSocket = this.socketFactory.getServerSocket(this.portNumber);
			while (true) {
				Socket socket= serverSocket.accept();
				if (!isActive()) {
					socket.close();
				} else {
					try {
						TcpConnection tcpConnection = (TcpConnection)this.connectionManager.createConnection(socket);
						initializeConnection(tcpConnection);
						execute(tcpConnection);
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
				execute(this);
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
		cleanUp();
	}
	
	private void cleanUp() {
		this.connectionManager.closeConnections();
		this.threadsExecutor.stop();
	}

	private void initializeConnection(TcpConnection connection) {
		connection.setListener(this.listener);
		connection.setMapper(this.mapper);
		connection.setSerializer(this.serializer);
		connection.setDeserializer(this.deserializer);
		connection.setApplicationEventPublisher(applicationEventPublisher);
		connection.setConnectionFactory(this.connectionManager);
	}

	private void execute(Runnable runnable) {
		if (!this.active) {
			throw new RuntimeException("Connection Factory not started");
		}
		this.threadsExecutor.execute(runnable);
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
	public void setConnnectionManager(ConnectionManager connectionFactory) {
		this.connectionManager = connectionFactory;
	}

	@Override
	public void setSocketFactory(SocketFactory socketFactory) {
		this.socketFactory = socketFactory;
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
	public void setThreadsExecutor(ThreadsExecutor threadsExecutor) {
		this.threadsExecutor = threadsExecutor;
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
