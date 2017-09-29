package com.app.hos.tests.units.managers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.Message;

import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.CommandFactory;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.type.CommandType;

public class CommandManagerTest {

	@Autowired
	@InjectMocks
	private CommandManager manager;
	
	@Mock
	private DeviceManager deviceManager;
	
	@Mock
	private Server server;
	
	private static MessageHeaders headers;
	
	@BeforeClass
	public static void prepareTests () {
		
		Map<String,Object> headerMap = new HashMap<String,Object>();
		headerMap.put(IpHeaders.HOSTNAME.toString(), "localhost");
		headerMap.put(IpHeaders.IP_ADDRESS.toString(), "192.168.0.21");
		headerMap.put(IpHeaders.REMOTE_PORT.toString(), "2345");
		headerMap.put(IpHeaders.CONNECTION_ID.toString(), "192.168.0.21-2345:123:asd:122:dfg");
		
		headers =  new MessageHeaders(headerMap);
	}
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void executeCommandManagerWithHelloCommand() {
		Mockito.doNothing().when(server).sendMessage(Mockito.any(Message.class));
		Mockito.doNothing().when(deviceManager).openDeviceConnection(Mockito.any(MessageHeaders.class), Mockito.any(String.class), Mockito.any(String.class));
		Command command = CommandFactory.getCommand(CommandType.HELLO);
		manager.executeCommand(headers, command);
		
		Mockito.verify(server, Mockito.times(1)).sendMessage(Mockito.any(Message.class));
		
	}
	
	@Test
	public void executeCommandManagerWithDeviceStatusCommand() {
		
	}
	
	@Test
	public void testExecuteCommandMethod() {
		Mockito.doNothing().when(deviceManager).addDeviceStatus("", null);; 
		deviceManager.addDeviceStatus("", null);
		String t = null;
		Assert.assertNull(t);
	}
}
