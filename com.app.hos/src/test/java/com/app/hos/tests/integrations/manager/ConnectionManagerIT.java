package com.app.hos.tests.integrations.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
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

import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.config.repository.SqlitePersistanceConfig;
import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.service.managers.connection.ConnectionManager;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.utils.DateTime;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;


@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class, ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectionManagerIT {
	
	@Autowired
    private DeviceManager deviceManager;
	
	@Autowired
    private ConnectionManager connectionManager;

	private static MessageHeaders headers;
	private static Device device;

	@BeforeClass
	public static void prepareDataForTests() {
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,"192.168.0.12:3456:123:asd:dsa:213");
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.12");
		headerMap.put(IpHeaders.REMOTE_PORT,3456);
		headerMap.put(IpHeaders.HOSTNAME,"localhost");
		headers = new MessageHeaders(headerMap);
		device = new Device("Device1", "serial_main_device");
	}
	
	private List<Device> getDevices() {
		Map<Device,DeviceStatus> devicStauses = deviceManager.getLatestDevicesStatuses();
		List<Device> devices = new LinkedList<Device>();
		devices.addAll(devicStauses.keySet());
		return devices;
	}
	
	@Test
	public void stage10_generateHistoryConnectionShouldReturnProperDateTime() {
		deviceManager.openDeviceConnection(headers, device.getName(), device.getSerial());
		List<Device> devices = getDevices();
		Assert.assertTrue(devices.size() == 1);
		Connection connection = devices.get(0).getConnection();
		Assert.assertNull(connection.getEndConnectionTime());
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		connectionManager.generateHistoryConnection(connectionId);
		devices = getDevices();
		Device device = devices.get(0);
		connection = device.getConnection();
		DateTime connectionTime = connection.getConnectionTime();
		DateTime endConnectionTime = connection.getEndConnectionTime();
		Assert.assertNotNull(endConnectionTime);
		Assert.assertNotEquals(connectionTime, endConnectionTime);
	}

}

//@Test
//public void stage50_setEndConnectionTimeAndCheckShouldReturnProperDateTime() {
//	Device device = deviceRepository.findBySerialNumber("serial_device_1");
//	DateTime endConnectionTime = new DateTime();
//	device.getConnection().setEndConnectionTime(endConnectionTime);
//	deviceRepository.save(device);
//	device = deviceRepository.findBySerialNumber("serial_device_1");
//	Assert.assertEquals(endConnectionTime, device.getConnection().getEndConnectionTime());
//}

//    @Test
//    @Rollback(true)
//    public void stage1_addNewDeviceMethodShouldAddEntryToDatabseLog() {
//    	manager.addConnection(connection);
//    	Collection<String> logsRows = loggingRepository.findAll();
//    	List<String> logs = new LinkedList<String>(logsRows);
//    	Assert.assertTrue(logs.size() == 1);
//    	logsRows = loggingRepository.findLogForLevel(Level.INFO.toString());
//    	logs = new LinkedList<String>(logsRows);
//    	Assert.assertTrue(logs.size() == 1);
//    }
    

