package com.app.hos.server.factory;

import java.net.Socket;
import java.net.SocketException;

import com.app.hos.server.connection.Connection;

public interface ConnectionManager {

	public Connection createConnection(Socket socket) throws SocketException;

	public Connection getConnection(String connectionId);
	
	public void addConnection(Connection connection);
	
	public void removeConnection(String connectionId);
	
	public void closeConnection(String connectionId);
	
	public void closeConnections();

}
