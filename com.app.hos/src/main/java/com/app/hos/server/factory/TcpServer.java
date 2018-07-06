package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.TcpListener;
import com.app.hos.server.TcpMessageMapper;
import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.TcpConnection;
import com.app.hos.server.serializer.ByteArrayDeserializer;

public class TcpServer implements Server, ConnectionFactory, Runnable {

	private final int portNumber;
	
	private volatile ServerSocket serverSocket;
	
	private final Map<String, Connection> connections = new ConcurrentHashMap<String, Connection>();
	
	private final Object monitor = new Object();
	
	private volatile ExecutorService connectionExecutor;
	
	private volatile boolean active;

	private volatile Serializer<?> serializer;
	
	private volatile Deserializer<?> deserializer  = new ByteArrayDeserializer();
	
	private TcpMessageMapper mapper;
	
	private TcpListener listener;
	
	public TcpServer (int portNumber) {
		this.portNumber = portNumber;
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		if (this.listener == null) {
			// TODO:
			//	throw exception
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
					} catch (SocketException e) {
						// log: failed to create and configure a TcpConnection for the new socket
						socket.close();
					}
				}
				// publish connection open event
			}
		} catch (IOException e) {
			publishServerEvent(e);
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
		addConnetion(connection);
	}
	
	private void addConnetion(Connection connection) {
		synchronized(this.connections) {
			if (!this.active) {
				connection.close();
				return;
			}
			this.connections.put(connection.getConnectionId(), connection);
		}
	}
	
	private Executor getTaskExecutor() {
		if (!this.active) {
			throw new RuntimeException("Connection Factory not started"); // TODO
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
	
	private void publishServerEvent(Throwable cause) {
		// TODO: implement
	}

	@Override
	public void setMapper(Object mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSerializer(Serializer<?> serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeserializer(Deserializer<?> deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(TcpListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}
