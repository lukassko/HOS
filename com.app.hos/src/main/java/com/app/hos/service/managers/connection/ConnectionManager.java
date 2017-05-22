package com.app.hos.service.managers.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Service;

import com.app.hos.service.managers.device.DeviceManager;


@Service
public class ConnectionManager implements CloseConnection {

	private ApplicationContext appContext;
	private DeviceManager deviceManager;
	
	@Autowired
	public ConnectionManager(ApplicationContext appContext, DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
		this.appContext = appContext;
	}
	
	public void closeConnection(String connectionId) {
		deviceManager.removeConnectedDevice(connectionId);
		closeTcpConnection(connectionId);
	}

	private boolean closeTcpConnection(String connectionId) {
		AbstractConnectionFactory connectionFActory = (AbstractConnectionFactory)appContext.getBean("hosServer");
		return connectionFActory.closeConnection(connectionId);
	}

}
