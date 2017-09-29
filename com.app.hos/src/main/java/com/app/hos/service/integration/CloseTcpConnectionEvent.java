package com.app.hos.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;

import com.app.hos.service.SystemFacade;

public class CloseTcpConnectionEvent {

	@Autowired
	private SystemFacade systemFacade;
	
	public void closeConnection (TcpConnectionCloseEvent event) {
		systemFacade.closeConnection(event.getConnectionId());
	}
	
}
