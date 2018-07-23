package com.app.hos.server.factory;

import com.app.hos.server.connection.Connection;

public interface ConnectionFactory {

	public Connection getConnection(String connectionId);
	
	public void addConnection(Connection connection);
	
	public void removeConnection(Connection connection);
	
	public void closeConnections();

}
