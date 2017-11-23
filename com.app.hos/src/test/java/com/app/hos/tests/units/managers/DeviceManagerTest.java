package com.app.hos.tests.units.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.utils.DateTime;

@Ignore("run only one integration test")
public class DeviceManagerTest {
	
	@Autowired
	@InjectMocks
	private DeviceManager manager;
	
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
        manager.openDeviceConnection(headers, device.getName(), device.getSerial());
    }
	
	@Test
	public void createDeviceMethodShouldThrowNoResultExceptionWhileFindingDeviceAndCallSaveMethod() {
		Mockito.doThrow(new NoResultException()).when(deviceRepository).find(Mockito.anyInt());
        Mockito.doNothing().when(deviceRepository).save(Mockito.any(Device.class));
		Mockito.verify(deviceRepository, Mockito.times(1)).save(Mockito.any(Device.class));
	}

	
	@Test
	public void getDeviceStatusesWhenNoDatainDbShouldReturnEmptyMap () {
        Mockito.when(deviceRepository.findAll()).thenReturn(new ArrayList<Device>());
		Map<Device, DeviceStatus> statuses = manager.getLatestDevicesStatuses();
		Assert.assertTrue(statuses.isEmpty());
	}
	
	@Test
	public void getDeviceStatusesShouldCallForAllDeviceDb () {
        Mockito.when(deviceRepository.findAll()).thenReturn(new ArrayList<Device>());
        manager.getLatestDevicesStatuses();
        Mockito.verify(deviceRepository, Mockito.times(1)).findAll();
	}

}

