package com.app.hos.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;

import com.app.hos.service.managers.connection.ConnectionManager;

public class CloseTcpConnectionEvent {

	@Autowired
	private ConnectionManager connectionManager;
	
	public void closeConnection (TcpConnectionCloseEvent event) {
		connectionManager.closeConnection(event.getConnectionId());
	}
	
}
