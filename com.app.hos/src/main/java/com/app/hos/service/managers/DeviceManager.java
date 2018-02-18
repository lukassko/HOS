package com.app.hos.service.managers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.app.hos.share.utils.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.persistance.repository.DeviceRepository;

@Service
@Transactional
public class DeviceManager {
	
	@Autowired
	private DeviceRepository deviceRepository;
		
	//need to find device at first, later create if not exist
	public void openDeviceConnection(MessageHeaders messageHeaders, String name, String serial) {
		//System.out.println("\n\nRun real method openDeviceConnection!\n\n");
		Connection connection = createNewConnection(messageHeaders);
		try {
			Device device = deviceRepository.findBySerialNumber(serial);
			device.setConnection(connection);
			connection.setDevice(device);
		} catch (NoResultException e) {
			Device device = createNewDevice(messageHeaders,name,serial);
			device.setConnection(connection);
			connection.setDevice(device);
			deviceRepository.save(device);
		}
	}

	// sort statuses by time and get the latest status of device; 
	// TEST SORTING WITH JUNIT
	public Map<Device, DeviceStatus> getConnectedDevices() {
		Map<Device, DeviceStatus> deviceStatus = new HashMap<Device, DeviceStatus>();
		Collection<Device> devices = deviceRepository.findAll();
		for (Device device : devices) {
			deviceStatus.put(device, device.getLastStatus());
		}
		return deviceStatus;
	}
	
	public List<DeviceStatus> getDeviceStatuses(String serial, DateTime from, DateTime to) {
		
		List<DeviceStatus> sortedStatus = new LinkedList<DeviceStatus>();
		
		try {
			List<DeviceStatus> statuses = deviceRepository.findBySerialNumber(serial).getDeviceStatuses();
			
			statuses.forEach(status -> {
				DateTime date = status.getTime();
				if (date.compareTo(from) >= 0 && date.compareTo(to) <= 0) 
					sortedStatus.add(status);			
			});
			
			Collections.sort(sortedStatus);
			
		} catch (NoResultException e) {
			
		}
		
		
		return sortedStatus;
	}
	
	public void addDeviceStatus(String serial, DeviceStatus deviceStatus) {
		Device device = deviceRepository.findBySerialNumber(serial);
		List<DeviceStatus> statuses = device.getDeviceStatuses();
		statuses.add(deviceStatus);
		//deviceRepository.save(device);
		// i suppose that need to call 'save' method on device
	}

	public Device findDeviceBySerial(String serial) {
		Device device;
		try {
			device = deviceRepository.findBySerialNumber(serial);
		} catch (NoResultException e) {
			device = null;
		}
		return device;
	}
	
	public void removeDevice(Device device) {
		deviceRepository.remove(device);
	}

	private Connection createNewConnection(MessageHeaders headers) {
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		String ip = headers.get(IpHeaders.IP_ADDRESS).toString();
	    int remotePort = (Integer) headers.get(IpHeaders.REMOTE_PORT);
	    String hostname = headers.get(IpHeaders.HOSTNAME).toString();
		DateTime connectionTime = new DateTime();
		return new Connection(connectionId, hostname, ip, remotePort, connectionTime);
	}
	
	private Device createNewDevice(MessageHeaders headers, String name, String serial) {
		Device device = new Device(name,serial);
		Connection connection = createNewConnection(headers);
		device.setConnection(connection);
		return device;
	}

}
