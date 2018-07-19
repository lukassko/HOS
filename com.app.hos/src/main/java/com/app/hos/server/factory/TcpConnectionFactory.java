package com.app.hos.server.factory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.app.hos.server.connection.Connection;

public class TcpConnectionFactory implements ConnectionFactory {

	
	private final Map<String, Connection> connections = new ConcurrentHashMap<String, Connection>();
	
	@Override
	public Connection getConnection(String connectionId) {
		return this.connections.get(connectionId);
	}

	@Override
	public void addConnection(Connection connection) {
		synchronized(this.connections) {
			this.connections.put(connection.getConnectionId(), connection);
		}
	}

	@Override
	public void removeConnection(Connection connection) {
		synchronized(this.connections) {
			this.connections.remove(connection.getConnectionId());
		}
	}

	@Override
	public void closeConnections() {
		synchronized(this.connections) {
			Iterator<Entry<String,Connection>> iterator= this.connections.entrySet().iterator();
			while(iterator.hasNext()) {
				Connection connection = iterator.next().getValue();
				connection.close();
				iterator.remove();
			}
		}
	}

}
