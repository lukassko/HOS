package com.app.hos.server;

import java.net.Socket;

public class Connection {

	private String connectionId;
	
	private Socket socket;
	
	public Connection(Socket socket) {
		this.socket = socket;
		connectionId = getConnectionId(socket);
	}

	
	public String getConnectionId() {
		return connectionId;
	}


	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}


	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	private String getConnectionId (Socket socket) {
		StringBuilder connctionidBuilder = new StringBuilder();
		connctionidBuilder.append(socket.getInetAddress().toString());
		connctionidBuilder.append(socket.getPort());
		connctionidBuilder.append(socket.getLocalPort());
		connctionidBuilder.append(socket.getPort());
		return connctionidBuilder.toString();
	}
}
