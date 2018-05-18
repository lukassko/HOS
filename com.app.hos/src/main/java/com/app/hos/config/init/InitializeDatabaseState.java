package com.app.hos.config.init;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.models.user.User;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.persistance.repository.UserRepository;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.share.utils.DateTime;

@Component
//@Profile({"!web-integration-test", "!integration-test"})
@Profile("!web-integration-test")
public class InitializeDatabaseState implements ApplicationListener<ContextRefreshedEvent>, Ordered {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public int getOrder() {
		return 10;
	}
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		addUser();
		addDevice();
	}
	
	private void addUser() {
		User user = new User("Lukasz");
		user.setPassword("admin");
		userRepository.save(user);
	}
	
	private void addDevice() {
		DeviceTypeEntity deviceType = deviceRepository.findType(DeviceType.PHONE);
		Connection connection = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost1", "192.168.0.21", 23451, new DateTime());
		Device device = new Device("Lukasz Device", "serial_1",deviceType);
		
		connection.setDevice(device);
		device.setConnection(connection);
		
		device.setDeviceStatuses(generateRandomStatus(24, 3600000));
		
		deviceRepository.save(device);
		
		connection = new Connection("192.168.0.21:23451-09:oa9:sd2", 
    			"localhost2", "192.168.0.89", 43219, new DateTime());
		device = new Device("Smartphone123", "serial_2",deviceType);
		
		connection.setDevice(device);
		device.setConnection(connection);

		device.setDeviceStatuses(generateRandomStatus(24, 3600000));
		
		deviceRepository.save(device);
		
		connection = new Connection("192.168.0.21:23451-09:oa9:sd2", 
    			"localhost3", "192.168.0.66", 7893, new DateTime());
		device = new Device("Device 1", "serial_3",deviceType);
		
		connection.setDevice(device);
		device.setConnection(connection);

		
		device.setDeviceStatuses(generateRandomStatus(24, 3600000));
		
		deviceRepository.save(device);
	}
	
	private List<DeviceStatus> generateRandomStatus(int size, int resolution) {
		List<DeviceStatus> statuses = new LinkedList<>();
		long DIFF = resolution; // milliseconds
		long timestamp = new DateTime().getTimestamp();
		for (int i = 0; i < size; i++) {
			statuses.add(new DeviceStatus(new DateTime(timestamp), 
								com.app.hos.utils.Utils.generateRandomDouble(), 
									com.app.hos.utils.Utils.generateRandomDouble()));
			timestamp = timestamp - DIFF;
		}
		return statuses;
	}
	
}
