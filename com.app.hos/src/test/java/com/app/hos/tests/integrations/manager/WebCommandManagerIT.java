package com.app.hos.tests.integrations.manager;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.share.utils.DateTime;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;

@Ignore("run only one integration test")
@ActiveProfiles("integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class,ApplicationContextConfig.class})
public class WebCommandManagerIT {

	@Autowired
	private WebCommandFactory webCommandFactory;
	
	@Autowired
	private DeviceManager deviceManager;
	
	@Test
	public void stage00_getAllDevicesWhenNoConnectedDeviceShouldReturnEmptyString() {
		WebCommandType type = WebCommandType.GET_ALL_DEVICES;
		WebCommand command = webCommandFactory.getCommand(type);
		Assert.assertNotNull(command);
		Assert.assertTrue(command.getType().equals(type));
		Assert.assertTrue(command.getMessage().equals("[]"));
	}

	@Test
	public void stage10_getAllDevicesWithOneConnectedDeviceWithoutStatusShouldReturnProperMessage() {
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,"192.168.0.100:13020:100:asd:dsa:100");
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.100");
		headerMap.put(IpHeaders.REMOTE_PORT,13020);
		headerMap.put(IpHeaders.HOSTNAME,"localhost");
		MessageHeaders headers = new MessageHeaders(headerMap);
		
		deviceManager.openDeviceConnection(headers, "device_1", "serial_device_1");
		WebCommand command = webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		String matchPattern = "(.*)webdevice(.*)endConnectionTime(.*):null,(.*)new(.*):false}},(.*)null(.*):null}(.*)";
		Assert.assertTrue(command.getMessage().matches(matchPattern));
		matchPattern = "(.*)null..null}]";
		Assert.assertTrue(command.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage20_getAllDevicesWithOneConnectedDeviceWithOneStatusShouldReturnProperMessage() {
		DeviceStatus status = new DeviceStatus(new DateTime(),0.2, 13.4);
		deviceManager.addDeviceStatus("serial_device_1", status);
		WebCommand command = webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		String matchPattern = "...webdevice(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:..time.:(.*)"+
					"ramUsage.:0.2,.cpuUsage.:13.4...";
		Assert.assertTrue(command.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage30_getAllDevicesWithOneConnectedDeviceWithManyStatusesShouldReturnProperMessage() {
		DeviceStatus status = new DeviceStatus(new DateTime(),11.65, 33.1);
		deviceManager.addDeviceStatus("serial_device_1", status);
		WebCommand command = webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		String matchPattern = "...webdevice(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:..time.:(.*)"+
					"ramUsage.:11.65,.cpuUsage.:33.1...";
		Assert.assertTrue(command.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage40_getAllDevicesWithTwoConnectedDevicesShouldReturnProperMessage() {
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,"192.168.0.100:13020:100:asd:dsa:101");
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.101");
		headerMap.put(IpHeaders.REMOTE_PORT,13021);
		headerMap.put(IpHeaders.HOSTNAME,"localhost_1");
		MessageHeaders headers = new MessageHeaders(headerMap);
		
		deviceManager.openDeviceConnection(headers, "device_2", "serial_device_2");
		WebCommand command = webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		
		String matchPattern = "...webdevice.:..id.:1,(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:..time.." + 
						 "(.*)ramUsage.:11.65,.cpuUsage.:33.1(.*)webdevice.:..id.:2(.*)new.:false..,.null.:null..";
		
		Assert.assertTrue(command.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage50_getAllDevicesWithTwoConnectedDevicesWithStatusesShouldReturnProperMessage() {
		DeviceStatus status = new DeviceStatus(new DateTime(),29.15, 89.13);
		deviceManager.addDeviceStatus("serial_device_2", status);
		WebCommand command = webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		String matchPattern = "...webdevice.:..id.:1,(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:"+
						"..time..(.*)ramUsage.:11.65,.cpuUsage.:33.1(.*)webdevice.:..id.:2(.*)"+
						"new.:false..,.devicestatus.:..time.:(.*)ramUsage.:29.15,.cpuUsage.:89.13...";
		Assert.assertTrue(command.getMessage().matches(matchPattern));
	}
}
