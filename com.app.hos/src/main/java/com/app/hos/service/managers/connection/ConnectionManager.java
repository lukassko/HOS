package com.app.hos.service.managers.connection;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.models.Connection;
import com.app.hos.service.managers.device.DeviceManager;


@Service
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ConnectionManager  {

	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private DeviceManager deviceManager;

	private Set<Connection> connections = new HashSet<Connection>();
	
	public ConnectionManager() {
	}
	
	public void addConnection(Connection connection) {
		System.out.println("ECECUTE! " + connection.toString());
		connections.add(connection);
	}
	
	public Connection getConnection(String connectionId) {
		for(Connection connection : connections) {
		    if (connection.getConnectionId().equals(connectionId)) {
		    	return connection;
		    }
		}
		return null;
	}
	
	public void closeConnection(String connectionId) {
		System.out.println("CLOSE");
		//deviceManager.removeConnectedDevice(connectionId);
		//closeTcpConnection(connectionId);
	}

	private boolean closeTcpConnection(String connectionId) {
		AbstractConnectionFactory connectionFActory = (AbstractConnectionFactory)appContext.getBean("hosServer");
		return connectionFActory.closeConnection(connectionId);
	}

}
