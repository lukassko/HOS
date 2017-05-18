package com.app.hos.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;

import com.app.hos.service.managers.connection.CloseConnection;

public class CloseTcpConnectionEvent {

	@Autowired
	private CloseConnection server;
	
	public void closeConnection (TcpConnectionCloseEvent event) {
		server.closeConnection(event.getConnectionId());
	}
	
}
