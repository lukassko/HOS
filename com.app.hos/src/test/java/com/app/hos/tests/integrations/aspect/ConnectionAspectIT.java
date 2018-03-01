package com.app.hos.tests.integrations.aspect;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.messaging.Message;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.api.SystemFacade;
import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.type.CommandType;


@Ignore("run only one integration test")
@WebAppConfiguration 
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class ConnectionAspectIT {
	
	// USE MOCK ON FACADE OBJECT CLASS AND TEST ASPECTS
	
	@InjectMocks
	@Autowired
	private SystemFacade systemFacade;
	
	@Mock
	private DeviceManager deviceManager;
	
	@Mock
	private ConnectionManager connectionManager;
	
	@Mock
	private Server server;
	
	@Autowired
	private LoggingRepository loggingRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Before
    public void initMocks(){
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testOpenConnectionAspect() {	
		Command command = CommandFactory.getCommand(CommandType.HELLO);
		String connectionId = "192.168.0.12:3456:123:asd:dsa:213";
		String serial = command.getSerialId();
		
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,connectionId);
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.12");
		headerMap.put(IpHeaders.REMOTE_PORT,3456);
		headerMap.put(IpHeaders.HOSTNAME,"localhost");
		MessageHeaders headers = new MessageHeaders(headerMap);

		
		Mockito.doNothing().when(deviceManager).openDeviceConnection(Mockito.any(MessageHeaders.class), Mockito.anyString() , Mockito.anyString());
		Mockito.doNothing().when(server).sendMessage(Mockito.<Message<Command>>any());

		systemFacade.receivedCommand(headers, command);
		
		// select logs
		Assert.assertTrue(loggingRepository.findAll().size() == 1 );
		Assert.assertTrue(loggingRepository.findLogForDevice(serial).size() == 1);
		Assert.assertTrue(loggingRepository.findLogForLevel(Level.INFO).size() == 1);
		
		// check if mock works and no devices was inserted to database
		Assert.assertTrue(deviceRepository.findAll().size() == 0);
	}
	
	// Exception should be thrown by Aspect
	@Test(expected = NoResultException.class)
	public void testCloseConnectionAspect() {	
		Mockito.doNothing().when(connectionManager).finalizeConnection(Mockito.anyString());
		String connectionId = "192.168.0.12:3456:123:asd:dsa:213";
		systemFacade.closeConnection(connectionId);
	}
	
}

    

