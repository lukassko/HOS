package com.app.hos.service.managers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.app.hos.share.command.result.NewDevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;

@Service
@Transactional
public class DeviceManager {
	
	@Autowired
	private DeviceRepository deviceRepository;
		
	//need to find device at first, later create if not exist
	public void openDeviceConnection(String connectionId, NewDevice device) {
		Connection connection = createConnection(connectionId,device);
		try {
			Device dev = deviceRepository.findBySerialNumber(device.getSerialId());
			dev.setConnection(connection);
			connection.setDevice(dev);
		} catch (NoResultException e) {
			Device dev = createNewDevice(device);
			dev.setConnection(connection);
			connection.setDevice(dev);
			deviceRepository.save(dev);
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
		Device device = findDeviceBySerial(serial);
		List<DeviceStatus> statuses = device.getDeviceStatuses();
		statuses.add(deviceStatus);
		//deviceRepository.save(device);
		// i suppose that need to call 'save' method on device
	}

	public Device findDeviceBySerial(String serial) {
		Device device;
		//try {
			device = deviceRepository.findBySerialNumber(serial);
		//} catch (NoResultException e) {
		//	device = null;
		//}
		return device;
	}
	
	public void removeDevice(Device device) {
		deviceRepository.remove(device);
	}

	private Connection createConnection(String connectionId, NewDevice device) {
		DateTime connectionTime = new DateTime();
		return new Connection.Builder().connectionId(connectionId)
										.ip(device.getIp())
										.remotePort(device.getPort())
										.hostname(device.getName())
										.connectionTime(connectionTime).build();
	}
	

	
	private Device createNewDevice(NewDevice newDevice) {
		// find device type in db, its tenporary
		DeviceTypeEntity deviceTypeEntity = new DeviceTypeEntity(newDevice.getType());
		return new Device(newDevice.getName(),newDevice.getSerialId(),deviceTypeEntity);
	}

}
