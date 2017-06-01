package com.app.hos.service.managers.device;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.app.hos.share.utils.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.share.command.result.DeviceStatus;

@Service
public class DeviceManager {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	private Map<String,Device> connectedDevices = new HashMap<String,Device>();
	private Map<Device,DeviceStatus> deviceStatuses = new HashMap<Device,DeviceStatus>();
	
	//need to find device at first, later create if not exist
	public void createDevice(MessageHeaders messageHeaders, String name, String serial) {
		String connectionId = messageHeaders.get(IpHeaders.CONNECTION_ID).toString();
		if (!isDeviceConnected(connectionId)) {
			Device device = findDevice(serial);
			if (device == null) {
				Device newDevice = createNewDevice(messageHeaders,name,serial);
				connectedDevices.put(connectionId, newDevice);
				deviceRepository.save(newDevice);
			} else {
				Connection connection = createNewConnection(messageHeaders);
				device.setConnection(connection);
			}
		}
	}

	public Set<Device> getConnectedDevices() {
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
	
	public Connection getConnectionBySerial(String serial) {
		Device device = getDeviceBySerialId(serial);
		if (device != null) {
			return device.getConnection();
		}
		return null;
	}

	private Device findDevice(String serial) {
		return deviceRepository.findDeviceBySerialNumber(serial);
	}
	
	private Device getDeviceBySerialId(String serialId) {
		for(Map.Entry<String, Device> entry : connectedDevices.entrySet()) {
			Device device = entry.getValue();
		    if (device.getSerial().equals(serialId)) {
		    	return device;
		    }
		}
		return null;
	}
	
	private boolean isDeviceConnected(String connectionId) {
		if (connectedDevices.containsKey(connectionId)) {
			return true;
		}
		return false;
	}
	
	private Connection createNewConnection(MessageHeaders headers) {
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		String ip = headers.get(IpHeaders.IP_ADDRESS).toString();
	    int remotePort = (Integer) headers.get(IpHeaders.REMOTE_PORT);
	    String hostname = headers.get(IpHeaders.HOSTNAME).toString();
		DateTime connectionTime = new DateTime();
		Connection connection = new Connection(connectionId, hostname, ip, remotePort, connectionTime);
		return connection;
	}
	
	private Device createNewDevice(MessageHeaders headers, String name, String serial) {
		Device device = new Device(name,serial);
		Connection connection = createNewConnection(headers);
		device.setConnection(connection);
		return device;
	}

}
