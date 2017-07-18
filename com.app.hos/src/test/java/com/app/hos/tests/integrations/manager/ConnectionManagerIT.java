package com.app.hos.tests.integrations.manager;


import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.config.AspectConfig;
import com.app.hos.logging.LoggingRepository;
import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.managers.connection.ConnectionManager;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.utils.DateTime;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;
import com.app.hos.tests.integrations.config.PersistanceConfig;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jboss.logging.Logger.Level;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
Test ConnectionManager class with Aspects and SQLite database
*/
@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class , AspectConfig.class, ApplicationContextConfig.class})
@ActiveProfiles("test-sqlite")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectionManagerIT {

	private static Device device;
	private static Connection connection;
	
	@Autowired
    private LoggingRepository loggingRepository;
	
	@Autowired
	@InjectMocks
	private ConnectionManager manager;
	
	@Mock
	private DeviceManager deviceManager;
	
	@Mock
	private ApplicationContext appContext;
	
	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@BeforeClass
    public static void beforeClass() {
		Connection testConnection = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost", "192.168.0.21", 23451, new DateTime());
		Device testDevice = new Device("Device", "98547kjyy1");
		
		testDevice.setConnection(testConnection);
		testConnection.setDevice(testDevice);

		device = testDevice;
		connection = testConnection;
    }
    
    @Test
    @Rollback(true)
    public void stage1_addNewDeviceMethodShouldAddEntryToDatabseLog() {
    	manager.addConnection(connection);
    	Collection<String> logsRows = loggingRepository.findAll();
    	List<String> logs = new LinkedList<String>(logsRows);
    	Assert.assertTrue(logs.size() == 1);
    	logsRows = loggingRepository.findLogForLevel(Level.INFO.toString());
    	logs = new LinkedList<String>(logsRows);
    	Assert.assertTrue(logs.size() == 1);
    }
    
}
