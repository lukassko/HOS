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
import com.app.hos.utils.Utils;

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
		
		device.setDeviceStatuses(generateRandomStatus());
		
		deviceRepository.save(device);
		
		connection = new Connection("192.168.0.21:23451-09:oa9:sd2", 
    			"localhost2", "192.168.0.89", 43219, new DateTime());
		device = new Device("Smartphone123", "serial_2");
		
		connection.setDevice(device);
		device.setConnection(connection);

		device.setDeviceStatuses(generateRandomStatus());
		
		deviceRepository.save(device);
		
		connection = new Connection("192.168.0.21:23451-09:oa9:sd2", 
    			"localhost3", "192.168.0.66", 7893, new DateTime());
		device = new Device("Device 1", "serial_3");
		
		connection.setDevice(device);
		device.setConnection(connection);

		device.setDeviceStatuses(generateRandomStatus());
		
		deviceRepository.save(device);
	}
	
	private List<DeviceStatus> generateRandomStatus() {
		List<DeviceStatus> statuses = new LinkedList<>();
		DateTime templateDate = new DateTime();
		long timestamp = templateDate.getTimestamp() - 86400000;
		for (int i = 0; i < 86400000; i = i + 10000) {
			statuses.add(new DeviceStatus(new DateTime(timestamp), Utils.generateRandomDouble(), Utils.generateRandomDouble()));
			timestamp = timestamp + 10000;
		}
		return statuses;
	}
}
