package com.app.hos.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;

import com.app.hos.service.api.ConnectionsApi;

public class CloseTcpConnectionEvent {

	@Autowired
	private ConnectionsApi connectionsApi;
	
	public void closeConnection (TcpConnectionCloseEvent event) {
		connectionsApi.closeConnection(event.getConnectionId());
	}
	
}
