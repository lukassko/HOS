package com.app.hos.tests.units.service.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.share.utils.DateTime;

public class DeviceManagerTest {
	
	@InjectMocks
	private DeviceManager manager = new DeviceManager();
	
	@Mock
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
	
	@Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(deviceRepository).save(Mockito.any(Device.class));
        manager.createDevice(headers, device.getName(), device.getSerial());
    }
	

	@Test
	public void createDeviceMethodShouldCallDeviceRepositorySaveMethod() {
		Mockito.verify(deviceRepository, Mockito.times(1)).save(Mockito.any(Device.class));
	}
	
	@Test
	public void createDeviceMethodShouldCallDeviceRepositoryFindMethod() {
		Mockito.when(deviceRepository.findDeviceBySerialNumber(Mockito.anyString())).thenReturn(device);
		Mockito.verify(deviceRepository, Mockito.times(1)).findDeviceBySerialNumber(device.getSerial());
	}
	
	@Test
	public void getConnectionForDeviceMethodShouldReturnConnection() {
		String serial = device.getSerial();
		Connection connection = manager.getConnectionBySerial(serial);
		Assert.assertNotNull(connection);
	}
	
	@Test
	public void createDeviceMethodShouldAddDeviceToMap() {
		int size = manager.getConnectedDevices().size();
		Assert.assertEquals(1, size);
	}
	
	@Test
	public void addDeviceStatusMethodShouldAddStatusToMap() {
		Connection connection = manager.getConnectionBySerial(device.getSerial());
		device.setConnection(connection);
		DeviceStatus status = new DeviceStatus(56.2, 13.4);
		manager.addDeviceStatus(device.getSerial(), status);
		Map<Device,DeviceStatus> statuses = manager.getDeviceStatuses();
		Assert.assertEquals(1, statuses.size());
		Assert.assertTrue(status.getCpuUsage() == statuses.get(device).getCpuUsage());

	}
}
