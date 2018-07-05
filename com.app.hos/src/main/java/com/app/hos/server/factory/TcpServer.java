package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.TcpListener;
import com.app.hos.server.TcpMessageMapper;
import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.TcpConnection;

public class TcpServer implements ConnectionFactory, Runnable {

	private final int portNumber;
	
	private volatile ServerSocket serverSocket;
	
	private final Map<String, Connection> connections = new ConcurrentHashMap<String, Connection>();
	
	private final Object monitor = new Object();
	
	private volatile Executor connectionExecutor;
	
	private volatile boolean active;
	
	private Serializer<?> serializer;
	
	private Deserializer<?> deserializer;
	
	private TcpMessageMapper mapper;
	
	private TcpListener listener;
	
	public TcpServer (int portNumber) {
		this.portNumber = portNumber;
	}

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
				TcpConnection tcpConnection = new TcpConnection(socket);
				initializeConnection(tcpConnection);
				// publish connection open event
			}
		} catch (IOException e) {
			
		}
	}

	@Override
	public Connection getConnection(String connectionId) {
		return this.connections.get(connectionId);
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
		closeActiveConnections();
	}
	
	private void closeActiveConnections() {
		if (this.active) {
			this.active = false;
			synchronized(this.connections) {
				
			}
		}
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
}
