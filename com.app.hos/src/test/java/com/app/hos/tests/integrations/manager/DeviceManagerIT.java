package com.app.hos.tests.integrations.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.config.AspectConfig;
import com.app.hos.logging.LoggingRepository;
import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.share.utils.DateTime;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;
import com.app.hos.tests.integrations.config.PersistanceConfig;

// get Collection of Devices when there is no device in database 
// get statuses from device from DB and getting from Map (check what with id field)
// check if getting AllDevices if from cache, not DB!
// test view what will be show when devices list eq 0
@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class , AspectConfig.class, ApplicationContextConfig.class})
@ActiveProfiles("test-sqlite")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceManagerIT {

	@Autowired
    private LoggingRepository loggingRepository;
	
	@Autowired
	private DeviceManager manager;
	
	@Autowired
    private DeviceRepository deviceRepository;

	private static MessageHeaders headers;
	private static Device device;
	private static Connection connection;

	@BeforeClass
	public static void prepareDataForTests() {
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,"192.168.0.12:3456:123:asd:dsa:213");
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.12");
		headerMap.put(IpHeaders.REMOTE_PORT,3456);
		headerMap.put(IpHeaders.HOSTNAME,"localhost");
		headers = new MessageHeaders(headerMap);
		device = new Device("Device1", "123123123");
		connection= new Connection("192.168.0.12:3456:123:asd:dsa:213", 
				"localhost", "192.168.0.12", 3456, new DateTime());
		device.setConnection(connection);
	}


	@Test
	//@Transactional
	@Rollback(true)
	public void stage10_createDeviceMethodShouldCallDeviceRepositorySaveMethod() {
		manager.createDevice(headers, device.getName(), device.getSerial());
		List<Device> devices = new ArrayList<Device>(deviceRepository.findAll());
		Assert.assertEquals(1, devices.size());
	}
	
	@Test
	public void stage20_getDeviceResultShoudGiveProperDeviceStatus() {
		System.out.println(device.toString());
		manager.createDevice(headers, device.getName(), device.getSerial());
		DeviceStatus status = new DeviceStatus(56.2, 13.4);
		manager.addDeviceStatus(device.getSerial(), status);
		Map<Device,DeviceStatus> deviceStatuses = manager.getDeviceStatuses();
		System.out.println(deviceStatuses.size());
		
		for(Map.Entry<Device, DeviceStatus> entry : deviceStatuses.entrySet()) {
			Device device = entry.getKey();
		    System.out.println(device.toString());
		}
		
		DeviceStatus tmpStatus = deviceStatuses.get(device);
		System.out.println(tmpStatus.toString());
		Assert.assertTrue(tmpStatus.getCpuUsage() == status.getCpuUsage());
	}
	
	@Test
	public void stage30_getDeviceResultShoudGiveProperDeviceStatus() {
		Device tmpDevice = new Device("tmpDev", "tmpSerial");
		manager.createDevice(headers, tmpDevice.getName(), tmpDevice.getSerial());
		Map<Device,DeviceStatus> deviceStatuses = manager.getDeviceStatuses();
		DeviceStatus tmpStatus = deviceStatuses.get(device);
		DateTime statusTime = tmpStatus.getTime();
		DateTime expectedTime = new DateTime(0);
		Assert.assertEquals(statusTime,expectedTime);
	}
	
}
