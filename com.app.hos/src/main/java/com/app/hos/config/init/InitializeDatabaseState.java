package com.app.hos.config.init;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.share.utils.DateTime;

@Component
//@Profile("init")
public class InitializeDatabaseState implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private DeviceRepository deviceRepository;
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Connection connection = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost1", "192.168.0.21", 23451, new DateTime());
		Device device = new Device("Lukasz Device", "serial_1");
		
		connection.setDevice(device);
		device.setConnection(connection);
		
		List<DeviceStatus> statuses = new LinkedList<DeviceStatus>();
		statuses.add(new DeviceStatus(23.32, 65.33));
		statuses.add(new DeviceStatus(13.25, 11.56));
		
		device.setDeviceStatuses(statuses);
		
		deviceRepository.save(device);
		
		connection = new Connection("192.168.0.21:23451-09:oa9:sd2", 
    			"localhost2", "192.168.0.89", 43219, new DateTime());
		device = new Device("Smartphone123", "serial_2");
		
		connection.setDevice(device);
		device.setConnection(connection);

		statuses = new LinkedList<DeviceStatus>();
		statuses.add(new DeviceStatus(89.11, 45.93));
		statuses.add(new DeviceStatus(65.27, 48.78));
		device.setDeviceStatuses(statuses);
		
		deviceRepository.save(device);
		
		connection = new Connection("192.168.0.21:23451-09:oa9:sd2", 
    			"localhost3", "192.168.0.66", 7893, new DateTime());
		device = new Device("Device 1", "serial_3");
		
		connection.setDevice(device);
		device.setConnection(connection);

		statuses = new LinkedList<DeviceStatus>();
		statuses.add(new DeviceStatus(19.21, 99.1));
		statuses.add(new DeviceStatus(32.21, 9.60));
		device.setDeviceStatuses(statuses);
		
		deviceRepository.save(device);
	}
	
}
