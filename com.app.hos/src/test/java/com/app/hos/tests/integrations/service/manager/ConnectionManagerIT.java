package com.app.hos.tests.integrations.service.manager;

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
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.service.managers.DeviceManager;
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
		Map<Device,DeviceStatus> devicStauses = deviceManager.getConnectedDevices();
		List<Device> devices = new LinkedList<Device>();
		devices.addAll(devicStauses.keySet());
		return devices;
	}
	
	@Test
	public void stage10_generateHistoryConnectionShouldReturnProperDateTimeForConnectionAndInsertToDb() {
		deviceManager.openDeviceConnection(headers, device.getName(), device.getSerial());
		List<Device> devices = getDevices();
		Assert.assertTrue(devices.size() == 1);
		Connection connection = devices.get(0).getConnection();
		Assert.assertNull(connection.getEndConnectionTime());
		
		try {
			Thread.sleep(1500);  // simulate connection period
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		connectionManager.finalizeConnection(connectionId);
		devices = getDevices();
		Device device = devices.get(0);
		connection = device.getConnection();
		DateTime connectionTime = connection.getConnectionTime();
		DateTime endConnectionTime = connection.getEndConnectionTime();
		Assert.assertNotNull(endConnectionTime);
		Assert.assertNotEquals(connectionTime, endConnectionTime); 
		
		List<HistoryConnection> historyConnections = new LinkedList<HistoryConnection>(
				connectionManager.findAllHistoryConnectionsByDeviceId(device.getId())
		);
		
		Assert.assertTrue(historyConnections.size() == 1);
		HistoryConnection historyConnection = historyConnections.get(0);
		Assert.assertEquals(historyConnection.getConnectionDateTime(), connectionTime);
	}

	@Test
	public void stage20_reconnectDeviceAndCheckEndConnectionTimeShouldReturnNull () {
		try {
			Thread.sleep(1500);  // simulate delay connection period
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		deviceManager.openDeviceConnection(headers, device.getName(), device.getSerial());
		List<Device> devices = getDevices();
		Assert.assertTrue(devices.size() == 1);
		Connection connection = devices.get(0).getConnection();
		Assert.assertNull(connection.getEndConnectionTime());
	}
	
	@Test
	public void stage30_endConnectionForDeviceShouldInsertOtherHistoryConnectionAndUpdateEndConnectionTime () {
		try {
			Thread.sleep(1500);  // simulate connection period
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		connectionManager.finalizeConnection(connectionId);
		List<Device> devices = getDevices();
		Device device = devices.get(0);
		Connection connection = device.getConnection();
		DateTime connectionTime = connection.getConnectionTime();
		DateTime endConnectionTime = connection.getEndConnectionTime();
		Assert.assertNotNull(endConnectionTime);
		Assert.assertNotEquals(connectionTime, endConnectionTime); 
		
		List<HistoryConnection> historyConnections = new LinkedList<HistoryConnection>(
				connectionManager.findAllHistoryConnectionsByDeviceId(device.getId())
		);
		
		Assert.assertTrue(historyConnections.size() == 2);
		HistoryConnection historyConnection = historyConnections.get(1);
		Assert.assertEquals(historyConnection.getConnectionDateTime(), connectionTime);
	}
	
}

    

