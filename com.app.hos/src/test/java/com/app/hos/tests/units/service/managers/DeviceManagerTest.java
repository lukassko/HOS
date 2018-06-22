package com.app.hos.tests.units.service.managers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.Utils;

public class DeviceManagerTest {
	
	@InjectMocks
	private DeviceManager manager = new DeviceManager();
	
	@Mock
	private DeviceRepository deviceRepository;
	
	@Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getDeviceStatusesMethodShouldReturnEmptyListWhenNoDataInDb () {
		// given
		String serial = "serial";
		Device device = new Device("name", serial, new DeviceTypeEntity(DeviceType.PHONE));
		when(deviceRepository.find(anyString())).thenReturn(device);
		
		// when
		List<DeviceStatus> statuses = manager.getDeviceStatuses(serial, new DateTime(), new DateTime());
		
		// then
		Assert.assertTrue(statuses.isEmpty());
	}

	@Test
	public void getDeviceStatusesMethodShouldReturnProperList () {
		// given
		long firstTime = 1529668800;
		long currenttTime = firstTime;
		String serial = "serial";
		
		List<DeviceStatus> statuses = new LinkedList<>();
		Device device = new Device("name", serial, new DeviceTypeEntity(DeviceType.PHONE));
		
		statuses.add(new DeviceStatus(new DateTime(currenttTime), Utils.generateRandomDouble(), Utils.generateRandomDouble()));
		currenttTime = firstTime + 2000;
		
		statuses.add(new DeviceStatus(new DateTime(currenttTime), Utils.generateRandomDouble(), Utils.generateRandomDouble()));
		currenttTime = firstTime + 6000;
		
		statuses.add(new DeviceStatus(new DateTime(currenttTime), Utils.generateRandomDouble(), Utils.generateRandomDouble()));
		currenttTime = firstTime + 10000;
		
		statuses.add(new DeviceStatus(new DateTime(currenttTime), Utils.generateRandomDouble(), Utils.generateRandomDouble()));
		currenttTime = firstTime + 12000;
		
		statuses.add(new DeviceStatus(new DateTime(currenttTime), Utils.generateRandomDouble(), Utils.generateRandomDouble()));
		device.setDeviceStatuses(statuses);
		
		when(deviceRepository.find(serial)).thenReturn(device);

		// when
		List<DeviceStatus> foundStatuses = manager.getDeviceStatuses(serial, new DateTime(firstTime + 3000), new DateTime(currenttTime));
		
		// then
		Assert.assertEquals(3, foundStatuses.size());
	}
	
	@Test
	public void getConnectedDevicesMethodShouldReturnEmptyMapWhenNoDataInDb () {
		// given
		when(deviceRepository.findAll()).thenReturn(new LinkedList<>());
		
		// when
		Map<Device, DeviceStatus> connectedDevices = manager.getConnectedDevices();
		
		// then
		Assert.assertTrue(connectedDevices.isEmpty());
	}


}

