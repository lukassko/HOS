package com.app.hos.service.managers.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class ConnectionManager implements CloseConnection {

	private ApplicationContext appContext;
	
	@Autowired
	public ConnectionManager(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	public void closeConnection(String connectionId) {
		//server.removeConnection(connectionId);
		closeTcpConnection(connectionId);
	}

	private boolean closeTcpConnection(String connectionId) {
		AbstractConnectionFactory connectionFActory = (AbstractConnectionFactory)appContext.getBean("hosServer");
		return connectionFActory.closeConnection(connectionId);
	}
}
