package com.app.hos.service.managers.device;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.app.hos.persistance.models.Device;

@Component
public class DeviceManager implements DeviceInformation,DeviceCreator {
	
	private Map<String,Device> connectedDevices = new HashMap<String,Device>();
		
	private void createNewDevice(MessageHeaders headers) {
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		String ip = headers.get(IpHeaders.IP_ADDRESS).toString();
	    String remotePort = headers.get(IpHeaders.REMOTE_PORT).toString();
	    String hostname = headers.get(IpHeaders.HOSTNAME).toString();
		DateTime connectionTime = new DateTime();
		Device connectedDevice = new Device(connectionId, hostname, ip, remotePort, connectionTime);
		this.connectedDevices.put(connectionId, connectedDevice);
	}
	
	
	private boolean isDeviceConnected(String connectionId) {
		if (connectedDevices.containsKey(connectionId)) {
			return true;
		}
		return false;
	}

	public Set<Device> getConnectedDevices() {
		Set<Device> devicesList = new HashSet<Device>();
		for (Device value : connectedDevices.values()) {
			devicesList.add(value);
	    }
		return devicesList;
	}

	public Device getDeviceStatus() {
		return null;
	}

	public boolean removeDevice(String connectionId) {
		return true;
	}

	public boolean addDevice(MessageHeaders messageHeaders) {
		String connectionId = messageHeaders.get(IpHeaders.CONNECTION_ID).toString();
		if (!isDeviceConnected(connectionId)) {
	    	createNewDevice(messageHeaders);
	    }
		return true;
	}
	
}
