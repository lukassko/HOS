package com.app.hos.tests.integrations.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

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
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.config.repository.SqlitePersistanceConfig;
import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.persistance.repository.ConnectionRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.share.utils.DateTime;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;

// get Collection of Devices when there is no device in database 
// get statuses from device from DB and getting from Map (check what with id field)
// check if getting AllDevices if from cache, not DB!
// test view what will be show when devices list eq 0

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class, ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceManagerIT {
	
	@Autowired
	private DeviceManager manager;
	
	@Autowired
    private DeviceRepository deviceRepository;

	@Autowired
    private ConnectionRepository connectionRepository;
	
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

	@Test
	public void stage05_getLatestDevicesStatusesWithoutAnyConnectedDeviceShouldReturnEmptyMap() {
		Map<Device,DeviceStatus> deviceStatuses = manager.getConnectedDevices();
		Assert.assertNotNull(deviceStatuses);
		Assert.assertTrue(deviceStatuses.isEmpty());
	}

	@Test
	public void stage10_createDeviceMethodShouldCallDeviceRepositorySaveMethod() {
		manager.openDeviceConnection(headers, device.getName(), device.getSerial());
		List<Device> devices = new ArrayList<Device>(deviceRepository.findAll());
		Assert.assertEquals(1, devices.size());
		device = devices.get(0);
		Assert.assertFalse(device.isNew());
	}
	
	@Test
	public void stage15_getStatusesFromDeviceWithoutAnyShouldReturnEmptyList() {
		List<DeviceStatus> stautuses = manager.getDeviceStatuses(device.getSerial(), new DateTime(0), new DateTime());
		Assert.assertTrue(stautuses.isEmpty());
	}
	
	@Test(expected = NoResultException.class) 
	public void stage16_addStatusWithValidSerialNumber() {
		DeviceStatus status = new DeviceStatus(new DateTime(),0.2, 13.4);
		manager.addDeviceStatus("invalid_serial", status);
	}
	
	@Test
	public void stage18_getLatestStatusForConnectedDeviceWithoutAnyShouldReturnMapWithNullAsKey() {
		Map<Device,DeviceStatus> deviceStatuses = manager.getConnectedDevices();
		Assert.assertTrue(deviceStatuses.size() == 1);
		Device device = null;
		DeviceStatus status = null;
		for (Map.Entry<Device, DeviceStatus> entry : deviceStatuses.entrySet()) {
		    device = entry.getKey();
		    status = entry.getValue();
		}
		Assert.assertNotNull(device);
		Assert.assertTrue(device.getSerial().equals(device.getSerial()));
		Assert.assertNull(status);
	}
	
	@Test
	public void stage20_addStatusToDeviceShouldReturnPorperStatuses() {
		DeviceStatus status = new DeviceStatus(new DateTime(),0.2, 13.4);
		manager.addDeviceStatus(device.getSerial(), status);
		Map<Device,DeviceStatus> deviceStatuses = manager.getConnectedDevices();
		Device device = null;
		for (Map.Entry<Device, DeviceStatus> entry : deviceStatuses.entrySet()) {
		    device = entry.getKey();
		    break;
		}
		Assert.assertNotNull(device);
		DeviceStatus tmpStatus = deviceStatuses.get(device);
		Assert.assertNotNull(tmpStatus);
		Assert.assertTrue(tmpStatus.getCpuUsage() == status.getCpuUsage());
		Assert.assertTrue(tmpStatus.getRamUsage() == status.getRamUsage());
		Assert.assertTrue(tmpStatus.getTime().equals(status.getTime()));
	}
	
	@Test
	@Transactional
	public void stage30_addMultiStatusToDeviceShouldReturnPorperStatusesSize() {
		manager.addDeviceStatus(device.getSerial(), new DeviceStatus(new DateTime(),0.1, 13.4));
		manager.addDeviceStatus(device.getSerial(), new DeviceStatus(new DateTime(),0.43, 22.5));
		manager.addDeviceStatus(device.getSerial(), new DeviceStatus(new DateTime(),0.89, 33.1));
		List<DeviceStatus> statuses = deviceRepository.findBySerialNumber(device.getSerial()).getDeviceStatuses();
		Assert.assertEquals(4, statuses.size());
		List<DeviceStatus> deviceStatuses = manager.getDeviceStatuses(device.getSerial(), new DateTime(0), new DateTime());
		Assert.assertEquals(4, deviceStatuses.size());
	}
	
	@Test
	@Transactional
	public void stage40_addMultiStatusToDeviceAndChekLastOneStatus() {
		manager.addDeviceStatus(device.getSerial(), new DeviceStatus(new DateTime(),0.39, 38.4));
		List<DeviceStatus> statuses = deviceRepository.findBySerialNumber(device.getSerial()).getDeviceStatuses();
		Assert.assertEquals(5, statuses.size());
		List<DeviceStatus> deviceStatuses = manager.getDeviceStatuses(device.getSerial(), new DateTime(0), new DateTime());
		Assert.assertEquals(5, deviceStatuses.size());
	}
	
	@Test
	public void stage45_addStatusToMultiDeviceAndChekLastOneStatus() throws InterruptedException {
		Map<Device,DeviceStatus> latestStauses = new HashMap<Device, DeviceStatus>();
		
		manager.openDeviceConnection(headers, "device_1", "serial_device_1");
		manager.openDeviceConnection(headers, "device_2", "serial_device_2");
		manager.openDeviceConnection(headers, "device_3", "serial_device_3");

		manager.addDeviceStatus("serial_device_1", new DeviceStatus(new DateTime(),1.32, 48.64));
		DeviceStatus status = new DeviceStatus(new DateTime(),0.39, 38.41);
		Thread.sleep(1600);
		manager.addDeviceStatus("serial_device_1", status);
		Device device = deviceRepository.findBySerialNumber("serial_device_1");
		Assert.assertNotNull(device);
		latestStauses.put(device, status);
		
		manager.addDeviceStatus("serial_device_2", new DeviceStatus(new DateTime(),11.34, 33.43));
		status = new DeviceStatus(new DateTime(),0.39, 38.41);
		Thread.sleep(1400);
		manager.addDeviceStatus("serial_device_2",status);
		device = deviceRepository.findBySerialNumber("serial_device_2");
		Assert.assertNotNull(device);
		latestStauses.put(device, status);
		
		manager.addDeviceStatus("serial_device_3", new DeviceStatus(new DateTime(),78.30, 68.47));
		status = new DeviceStatus(new DateTime(),32.33, 28.46);
		Thread.sleep(2400);
		manager.addDeviceStatus("serial_device_3", status);
		device = deviceRepository.findBySerialNumber("serial_device_3");
		Assert.assertNotNull(device);
		latestStauses.put(device, status);
		
		Map<Device,DeviceStatus> deviceStatuses = manager.getConnectedDevices();
		Assert.assertEquals(4, deviceStatuses.size()); // one device saved at the beginning
		
		for (Map.Entry<Device, DeviceStatus> entry : deviceStatuses.entrySet()) {
			Device mappedDevice = entry.getKey();
			DeviceStatus mappedStatus = entry.getValue();
			if (mappedDevice.getSerial().equals("serial_main_device")) { 
				continue; // one device saved at the beginning
			}
			Assert.assertNotNull(mappedStatus);
			DeviceStatus tmp = latestStauses.get(mappedDevice);
			Assert.assertNotNull(tmp);
			Assert.assertEquals(mappedStatus, tmp);
		}
	}
	
	@Test
	public void stage60_newConnectionForDeviceInDbShouldUpdateConnectionsParams () {	
		Device oldConnectedDevice = deviceRepository.findBySerialNumber("serial_device_1");
		
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put(IpHeaders.CONNECTION_ID,"192.168.0.122:13020:123:asd:dsa:213");
		headerMap.put(IpHeaders.IP_ADDRESS,"192.168.0.122");
		headerMap.put(IpHeaders.REMOTE_PORT,13020);
		headerMap.put(IpHeaders.HOSTNAME,"localhost");
		MessageHeaders headers = new MessageHeaders(headerMap);
		
		manager.openDeviceConnection(headers, "device_1", "serial_device_1");
		
		Device newConnectedDevice = deviceRepository.findBySerialNumber("serial_device_1");
		Connection oldConnection = oldConnectedDevice.getConnection();
		Connection newConnection = newConnectedDevice.getConnection();
		
		Assert.assertNotEquals(newConnection.getConnectionTime(), oldConnection.getConnectionTime());
		Assert.assertNull(newConnection.getEndConnectionTime());
	}
	
	//@Test <- move to ConnectionManagerIT
	public void stage50_disconnectDeviceAndCheckLastConnectionTime() {
		
	}
	
	//@Test <- move to ConnectionManagerIT
	public void stage60_disconnectDeviceAndCheckHistoryConnection() {
	}
	
	@Test(expected = NonUniqueResultException.class)
	public void stage70_removeDetachedEntityShouldThrowException() {
		manager.removeDevice(device);
		Assert.assertNull(deviceRepository.find(device.getId()));
		Connection connection = device.getConnection();
		connectionRepository.findConnectionById(connection.getConnectionId());
	}

}
