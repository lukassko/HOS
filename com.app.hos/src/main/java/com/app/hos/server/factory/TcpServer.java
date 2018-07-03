package com.app.hos.server.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.net.SocketServer;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.connection.Connection;

public class TcpServer implements ConnectionFactory, Runnable {

	private final int port;
	
	private SocketServer socketServer;
	
	private final Map<String, Connection> connections = new ConcurrentHashMap<String, Connection>();
	
	private final Object monitor = new Object();
	
	private volatile Executor connectionExecutor;
	
	private volatile boolean active;
	
	private Serializer<?> serializer;
	
	private Deserializer<?> deserializer;
	
	public TcpServer (int port) {
		this.port = port;
	}

	@Override
	public void run() {
		
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
		if (this.active) {
			this.active = false;
			synchronized(this.connections) {
				
			}
		}
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
