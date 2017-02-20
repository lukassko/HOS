package com.app.hos.service.integration.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.persistance.models.Device;
import com.app.hos.service.manager.CloseConnection;
import com.app.hos.share.command.Command;

public class Server {
	
	private Map<String,Device> connectedDevices = new HashMap<String,Device>();
	
	private ApplicationContext appContext;

	private Gateway gateway;
	
	@Autowired
	public Server(Gateway gateway, ApplicationContext appContext) {
		this.gateway = gateway;
		this.appContext = appContext;
	}

	public void receiveMessage(Message<byte[]> message) {
		String connectionId = message.getHeaders().get(IpHeaders.CONNECTION_ID).toString();
	    if (!isDeviceConnected(connectionId)) {
	    	createNewDevice(message.getHeaders());
	    }
	    try {
    		Command command = deserialize(message.getPayload());
    		executeCommand(command);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message<byte[]> message) {
		if(message != null) 
			this.gateway.send("test");
	}
	
	private Command deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    Object tmpObject = is.readObject();
	    return (Command)tmpObject;
	}
	
	private boolean isDeviceConnected(String connectionId) {
		if (connectedDevices.containsKey(connectionId)) {
			return true;
		}
		return false;
	}

	private void createNewDevice(MessageHeaders headers) {
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		String ip = headers.get(IpHeaders.IP_ADDRESS).toString();
	    String remotePort = headers.get(IpHeaders.REMOTE_PORT).toString();
	    String hostname = headers.get(IpHeaders.HOSTNAME).toString();
		DateTime connectionTime = new DateTime();
		Device connectedDevice = new Device(connectionId, hostname, ip, remotePort, connectionTime);
		this.connectedDevices.put(connectionId, connectedDevice);
	}
	
	private void executeCommand(Command command) {
	}
	
	public Set<Device> getDevicesList () {
		Set<Device> devicesList = new HashSet<Device>();
		for (Device value : connectedDevices.values()) {
			devicesList.add(value);
	    }
		return devicesList;
	}

	public void createTestNewDevice(String connId, Device device){
		this.connectedDevices.put(connId, device);
	}

	public void removeConnection(String connectionId) {
		connectedDevices.remove(connectionId);
	}
	
	public boolean closeConnection(String connectionId) {
		AbstractConnectionFactory connectionFActory = (AbstractConnectionFactory)appContext.getBean("hosServer");
		return connectionFActory.closeConnection(connectionId);
	}
}
