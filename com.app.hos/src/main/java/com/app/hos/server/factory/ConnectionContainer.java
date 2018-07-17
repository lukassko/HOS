package com.app.hos.server.factory;

import com.app.hos.server.connection.Connection;

public interface ConnectionContainer {

	public void addNewConnection(Connection connection);
		
	public void removeDeadConnection(Connection connection);
	
}
