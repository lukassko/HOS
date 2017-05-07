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
import com.app.hos.share.command.result.DeviceStatus;


@Component
public class DeviceManager {
	
	private Map<String,Device> connectedDevices = new HashMap<String,Device>();
	private Map<Device,DeviceStatus> deviceStatuses = new HashMap<Device,DeviceStatus>();
	
	public void createDevice(MessageHeaders messageHeaders, String name, String serial) {
		String connectionId = messageHeaders.get(IpHeaders.CONNECTION_ID).toString();
		System.out.println(connectionId);
		if (!isDeviceConnected(connectionId)) {
			System.out.println("ADD EV wit serial " + serial);
			Device device = createNewDevice(messageHeaders,name,serial);
			connectedDevices.put(connectionId, device);
		}
	}

	public Set<Device> getConnectedDevices() {
		System.out.println("GET DEVIE CONNECTED");
		Set<Device> devicesList = new HashSet<Device>();
		for (Device value : connectedDevices.values()) {
			devicesList.add(value);
	    }
		return devicesList;
	}

	public Map<Device, DeviceStatus> getDeviceStatuses() {
		return deviceStatuses;
	}
	
	public void addDeviceStatus(String serialId, DeviceStatus deviceStatus) {
		Device device = getDeviceBySerialId(serialId);
		if (device == null) {
			return;
		}
		deviceStatuses.put(device, deviceStatus);
	}
	
	public void removeConnectedDevice(String connectionId){
		Device device = connectedDevices.get(connectionId);
		deviceStatuses.remove(device);
		connectedDevices.remove(connectionId);
	}
	
	private Device getDeviceBySerialId(String serialId) {
		for(Map.Entry<String, Device> entry : connectedDevices.entrySet()) {
			Device device = entry.getValue();
			System.out.println("SERIAL "+device.getSerial());
			System.out.println("SERIAL received "+serialId);
		    if (device.getSerial().equals(serialId)) {
		    	return device;
		    }
		}
		return null;
	}
	
	private boolean isDeviceConnected(String connectionId) {
		System.out.println(connectedDevices.size());
		for(Map.Entry<String, Device> entry : connectedDevices.entrySet()) {
		    String key = entry.getKey();
		    System.out.println(key);
		}
		if (connectedDevices.containsKey(connectionId)) {
			System.out.println("OK");
			return true;
		}
		System.out.println("NOK");
		return false;
	}
	
	private Device createNewDevice(MessageHeaders headers, String name, String serial) {
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		String ip = headers.get(IpHeaders.IP_ADDRESS).toString();
	    String remotePort = headers.get(IpHeaders.REMOTE_PORT).toString();
	    String hostname = headers.get(IpHeaders.HOSTNAME).toString();
		DateTime connectionTime = new DateTime();
		Connection connection = new Connection(connectionId, hostname, ip, remotePort, connectionTime);
		return new Device(connection, name,serial);
	}

}
