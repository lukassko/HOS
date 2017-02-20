package com.app.hos.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.hos.service.integration.server.Server;

@Component
public class ConnectionManager implements CloseConnection {

	private Server server;
	
	@Autowired
	public ConnectionManager(Server server) {
		this.server = server;
	}
	
	public void removeConnection(String connectionId) {
		server.removeConnection(connectionId);
	}

}
