package com.app.hos.service.managers.device;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;


@Component
public class DeviceManager {
	
	private Map<String,Device> connectedDevices = new HashMap<String,Device>();
	
	public Device getDeviceStatus() {
		return null;
	}

	public boolean removeDevice(String connectionId) {
		return true;
	}

	public void createDevice(MessageHeaders messageHeaders, String name, String serial) {
		String connectionId = messageHeaders.get(IpHeaders.CONNECTION_ID).toString();
		Device device = createNewDevice(messageHeaders,name,serial);
		connectedDevices.put(connectionId, device);
	}

	public Set<Device> getConnectedDevices() {
		Set<Device> devicesList = new HashSet<Device>();
		for (Device value : connectedDevices.values()) {
			devicesList.add(value);
	    }
		return devicesList;
	}
	
	public void removeConnectedDevice(String connectionId){
		connectedDevices.remove(connectionId);
	}
	
	private Device createNewDevice(MessageHeaders headers, String name, String serial) {
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		String ip = headers.get(IpHeaders.IP_ADDRESS).toString();
	    String remotePort = headers.get(IpHeaders.REMOTE_PORT).toString();
	    String hostname = headers.get(IpHeaders.HOSTNAME).toString();
		DateTime connectionTime = new DateTime();
		Connection connection = new Connection(connectionId, hostname, ip, remotePort, connectionTime);
		return new Device(connection, name, serial);
	}
}
