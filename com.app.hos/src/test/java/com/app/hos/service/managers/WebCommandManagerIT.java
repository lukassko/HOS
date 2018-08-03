package com.app.hos.service.managers;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.common.TestUtils;
import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.config.repository.SqlitePersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.command.result.NewDevice;
import com.app.hos.service.command.type.DeviceType;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.service.websocket.WebSocketManager;
import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.builder_v2.WebCommandFactory;
import com.app.hos.utils.json.JsonConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Ignore("run only one integration test")
@WebAppConfiguration 
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class, ApplicationContextConfig.class})
public class WebCommandManagerIT {

	//@Autowired
	//private WebCommandFactory webCommandFactory;
	
	@Autowired
	private WebSocketManager webSocketManager;
	
	@Autowired
	private DeviceManager deviceManager;
	
	@Test
	public void stage00_getAllDevicesWhenNoConnectedDeviceShouldReturnEmptyString() 
				throws NotExecutableCommandException, InterruptedException, JsonParseException, JsonMappingException, IOException {
		
		final CountDownLatch afterExecuteCommand = new CountDownLatch(1);
		final List<WebCommand> sentCommands = new LinkedList<>();
		
		WebCommandType type = WebCommandType.GET_ALL_DEVICES;
		WebCommand command = WebCommandFactory.get(type);
		String message = JsonConverter.getJson(command);
		
		webSocketManager.executeCommand((s, c) -> {
			
			sentCommands.add(c);
			afterExecuteCommand.countDown();
			
		}, TestUtils.getSessionTest(), message);

		afterExecuteCommand.await(3, TimeUnit.SECONDS);
		
		Assert.assertEquals(1, sentCommands.size());
		
		WebCommand webCommand = sentCommands.get(0);
		
		Assert.assertTrue(webCommand.getStatus());
		
		Assert.assertTrue(webCommand.getType().equals(type));
		Assert.assertTrue(webCommand.getMessage().equals("[]"));
	}

	@Test
	public void stage10_getAllDevicesWithOneConnectedDeviceWithoutStatusShouldReturnProperMessage() 
			throws NotExecutableCommandException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		
		final CountDownLatch afterExecuteCommand = new CountDownLatch(1);
		final List<WebCommand> sentCommands = new LinkedList<>();
		
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,"192.168.0.100:13020:100:asd:dsa:100");
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.100");
		headerMap.put(IpHeaders.REMOTE_PORT,13020);
		headerMap.put(IpHeaders.HOSTNAME,"localhost");
		MessageHeaders headers = new MessageHeaders(headerMap);
		
		deviceManager.openDeviceConnection("connectionId", new NewDevice("serial", "name", DeviceType.PHONE, "ip", 123));
		
		WebCommand command = WebCommandFactory.get(WebCommandType.GET_ALL_DEVICES);
		String message = JsonConverter.getJson(command);
		
		webSocketManager.executeCommand((s, c) -> {
			
			sentCommands.add(c);
			afterExecuteCommand.countDown();
			
		}, TestUtils.getSessionTest(), message);
		
		afterExecuteCommand.await(3, TimeUnit.SECONDS);
		
		Assert.assertEquals(1, sentCommands.size());
		
		WebCommand webCommand = sentCommands.get(0);

		Assert.assertTrue(webCommand.getStatus());
		
		String matchPattern = "(.*)webdevice(.*)endConnectionTime(.*):null,(.*)new(.*):false}},(.*)null(.*):null}(.*)";
		Assert.assertTrue(webCommand.getMessage().matches(matchPattern));
		
		matchPattern = "(.*)null..null}]";
		Assert.assertTrue(webCommand.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage20_getAllDevicesWithOneConnectedDeviceWithOneStatusShouldReturnProperMessage() 
			throws NotExecutableCommandException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		
		final CountDownLatch afterExecuteCommand = new CountDownLatch(1);
		final List<WebCommand> sentCommands = new LinkedList<>();
		
		DeviceStatus status = new DeviceStatus(new DateTime(),0.2, 13.4);
		deviceManager.addDeviceStatus("serial_device_1", status);
		
		WebCommand command = WebCommandFactory.get(WebCommandType.GET_ALL_DEVICES);
		String message = JsonConverter.getJson(command);
		
		webSocketManager.executeCommand((s, c) -> {
			
			sentCommands.add(c);
			afterExecuteCommand.countDown();
			
		}, TestUtils.getSessionTest(), message);
		
		afterExecuteCommand.await(3, TimeUnit.SECONDS);
		
		Assert.assertEquals(1, sentCommands.size());
		
		WebCommand webCommand = sentCommands.get(0);

		Assert.assertTrue(webCommand.getStatus());
		
		String matchPattern = "...webdevice(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:..time.:(.*)"+
					"ramUsage.:0.2,.cpuUsage.:13.4...";
		Assert.assertTrue(webCommand.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage30_getAllDevicesWithOneConnectedDeviceWithManyStatusesShouldReturnProperMessage() 
			throws NotExecutableCommandException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		
		final CountDownLatch afterExecuteCommand = new CountDownLatch(1);
		final List<WebCommand> sentCommands = new LinkedList<>();

		DeviceStatus status = new DeviceStatus(new DateTime(),11.65, 33.1);
		deviceManager.addDeviceStatus("serial_device_1", status);
		
		WebCommand command = WebCommandFactory.get(WebCommandType.GET_ALL_DEVICES);
		String message = JsonConverter.getJson(command);
		
		webSocketManager.executeCommand((s, c) -> {
			
			sentCommands.add(c);
			afterExecuteCommand.countDown();
			
		}, TestUtils.getSessionTest(), message);
		
		afterExecuteCommand.await(3, TimeUnit.SECONDS);
		
		Assert.assertEquals(1, sentCommands.size());
		
		WebCommand webCommand = sentCommands.get(0);
				
		Assert.assertTrue(webCommand.getStatus());
		
		String matchPattern = "...webdevice(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:..time.:(.*)"+
					"ramUsage.:11.65,.cpuUsage.:33.1...";
		Assert.assertTrue(webCommand.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage40_getAllDevicesWithTwoConnectedDevicesShouldReturnProperMessage() 
			throws NotExecutableCommandException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		
		final CountDownLatch afterExecuteCommand = new CountDownLatch(1);
		final List<WebCommand> sentCommands = new LinkedList<>();
		
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,"192.168.0.100:13020:100:asd:dsa:101");
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.101");
		headerMap.put(IpHeaders.REMOTE_PORT,13021);
		headerMap.put(IpHeaders.HOSTNAME,"localhost_1");
		MessageHeaders headers = new MessageHeaders(headerMap);
		
		deviceManager.openDeviceConnection("connectionId", new NewDevice("serial", "name", DeviceType.PHONE, "ip", 123));
		
		WebCommand command = WebCommandFactory.get(WebCommandType.GET_ALL_DEVICES);
		String message = JsonConverter.getJson(command);
		
		webSocketManager.executeCommand((s, c) -> {
			
			sentCommands.add(c);
			afterExecuteCommand.countDown();
			
		}, TestUtils.getSessionTest(), message);
		
		afterExecuteCommand.await(3, TimeUnit.SECONDS);
		
		Assert.assertEquals(1, sentCommands.size());
		
		WebCommand webCommand = sentCommands.get(0);

		Assert.assertTrue(webCommand.getStatus());
		
		String matchPattern = "...webdevice.:..id.:1,(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:..time.." + 
						 "(.*)ramUsage.:11.65,.cpuUsage.:33.1(.*)webdevice.:..id.:2(.*)new.:false..,.null.:null..";
		
		Assert.assertTrue(webCommand.getMessage().matches(matchPattern));
	}
	
	@Test
	public void stage50_getAllDevicesWithTwoConnectedDevicesWithStatusesShouldReturnProperMessage() 
			throws NotExecutableCommandException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		final CountDownLatch afterExecuteCommand = new CountDownLatch(1);
		final List<WebCommand> sentCommands = new LinkedList<>();
		
		DeviceStatus status = new DeviceStatus(new DateTime(),29.15, 89.13);
		deviceManager.addDeviceStatus("serial_device_2", status);
		
		WebCommand command = WebCommandFactory.get(WebCommandType.GET_ALL_DEVICES);
		String message = JsonConverter.getJson(command);
		
		webSocketManager.executeCommand((s, c) -> {
			
			sentCommands.add(c);
			afterExecuteCommand.countDown();
			
		}, TestUtils.getSessionTest(), message);
		
		
		afterExecuteCommand.await(3, TimeUnit.SECONDS);
		
		Assert.assertEquals(1, sentCommands.size());
		
		WebCommand webCommand = sentCommands.get(0);
		
		Assert.assertTrue(webCommand.getStatus());
		
		String matchPattern = "...webdevice.:..id.:1,(.*)endConnectionTime.:null,.new.:false..,.devicestatus.:"+
						"..time..(.*)ramUsage.:11.65,.cpuUsage.:33.1(.*)webdevice.:..id.:2(.*)"+
						"new.:false..,.devicestatus.:..time.:(.*)ramUsage.:29.15,.cpuUsage.:89.13...";
		
		Assert.assertTrue(webCommand.getMessage().matches(matchPattern));
	}

}
