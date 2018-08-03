package com.app.hos.service.managers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.command.result.NewDevice;
import com.app.hos.service.exceptions.handler.ExceptionUtils;

@Service
@Transactional
public class DeviceManager {
	
	@Autowired
	private DeviceRepository deviceRepository;

	public void openDeviceConnection(String connectionId, NewDevice newDevice) {
		Connection connection = createConnection(connectionId,newDevice);
		Device device = deviceRepository.find(newDevice.getSerialId());
		if (device != null) {
			device.setConnection(connection);
			connection.setDevice(device);
		} else {
			Device dev = createNewDevice(newDevice);
			dev.setConnection(connection);
			connection.setDevice(dev);
			deviceRepository.save(dev);
		}
	}

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
			List<DeviceStatus> statuses = deviceRepository.find(serial).getDeviceStatuses();
			statuses.forEach(status -> {
				DateTime date = status.getTime();
				if (date.compareTo(from) >= 0 && date.compareTo(to) <= 0) 
					sortedStatus.add(status);			
			});
			Collections.sort(sortedStatus);
		} catch (NoResultException e) {
			ExceptionUtils.handle(e);
		}
		return sortedStatus;
	}
	
	public void addDeviceStatus(String serial, DeviceStatus deviceStatus) {
		Device device = findDevice(serial);
		if (device != null) {
			List<DeviceStatus> statuses = device.getDeviceStatuses();
			statuses.add(deviceStatus);
		}
	}

	public Device findDevice(int id) {
		return deviceRepository.find(id);
	}
	
	public Device findDevice(String serial) {
		return deviceRepository.find(serial);
	}
	
	public Device findDeviceByConnection(String connectionId) {
		return deviceRepository.findByConnection(connectionId);
	}
	
	public void removeDevice(Device device) {
		deviceRepository.remove(device);
	}

	private Connection createConnection(String connectionId, NewDevice device) {
		return new Connection.Builder().connectionId(connectionId)
										.ip(device.getIp())
										.remotePort(device.getPort())
										.hostname(device.getName())
										.connectionTime(new DateTime()).build();
	}
	
	private Device createNewDevice(NewDevice newDevice) {
		DeviceTypeEntity deviceType = deviceRepository.findType(newDevice.getType());
		return new Device(newDevice.getName(),newDevice.getSerialId(),deviceType);
	}

}
