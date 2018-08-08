package com.app.hos.server.factory;

import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.TcpConnection;

public class TcpConnectionManager implements ConnectionManager {

	private final Map<String, Connection> connections = new ConcurrentHashMap<String, Connection>();
	
	//TODO maybe factory to create connections with different socket attribute 
	@Override
	public Connection createConnection(Socket socket) throws SocketException {
		Connection connection = new TcpConnection.ConnectionBuilder().socket(socket).build();
		addConnection(connection);
		return connection;
	}
	
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
	public void removeConnection(String connectionId) {
		synchronized(this.connections) {
			this.connections.remove(connectionId);
		}
	}

	@Override
	public void closeConnections() {
		synchronized(this.connections) {
			Iterator<Entry<String,Connection>> iterator= this.connections.entrySet().iterator();
			while(iterator.hasNext()) {
				Connection connection = iterator.next().getValue();
				closeConnection(connection.getConnectionId());
			}
		}
	}

	@Override
	public void closeConnection(String connectionId) {
		synchronized(this.connections) {
			Connection connection = this.connections.get(connectionId);
			if (connection != null) {
				connection.close();
				this.connections.remove(connectionId);
			}

		}
	}
}
