package com.app.hos.service.managers;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.common.MultithreadExecutor;
import com.app.hos.config.ApplicationContextConfig;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.command.result.NewDevice;
import com.app.hos.service.command.type.DeviceType;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.utils.Utils;

@Ignore("run only one integration test")
@WebAppConfiguration 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceManagerMultithreadIT {
	
	@Autowired
	private DeviceManager deviceManager;
	
	@Test
	public void stage05_connectMultipleDeviceAndCheckIfAllWasSaved() {
		
		// given
		Set<Device> devices = deviceManager.getConnectedDevices().keySet();
		Assert.assertEquals(0, devices.size());
		
		List<Runnable> runnables = new LinkedList<>();
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				NewDevice device = new NewDevice("serial_1", "name_1", DeviceType.SERVER, "192:168:0:1", 2221);
				deviceManager.openDeviceConnection("192.168.0.1:1111:123:asd:dsa:213", device);
			}
		};
		
		runnables.add(runnable);
		
		// when 
		boolean ended;
		try {
			MultithreadExecutor.assertConcurrent(runnables,1000);
			ended = true;
		} catch (InterruptedException e) {
			ended = false;
		}
		
		// then
		Assert.assertTrue(ended);
		Assert.assertTrue(ended);
		devices = deviceManager.getConnectedDevices().keySet();
		Assert.assertEquals(1, devices.size());
	}
	
	//@Test
	public void stage10_connectMultipleDeviceAndCheckIfAllWasSaved() {
		
		// given
		Set<Device> devices = deviceManager.getConnectedDevices().keySet();
		//Assert.assertEquals(1, devices.size());
		
		List<Runnable> runnables = new LinkedList<>();
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				NewDevice device = new NewDevice("serial_1", "name_1", DeviceType.SERVER, "192:168:0:1", 2221);
				deviceManager.openDeviceConnection("192.168.0.1:2221:123:asd:dsa:1", device);
			}
		};

		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run()  {
				NewDevice device = new NewDevice("serial_2", "name_2", DeviceType.SERVER, "192:168:0:2", 2222);
				deviceManager.openDeviceConnection("192.168.0.2:2222:123:asd:dsa:2", device);
			}
		};
		
		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run() {
				NewDevice device = new NewDevice("serial_3", "name_3", DeviceType.SERVER, "192:168:0:3", 2223);
				deviceManager.openDeviceConnection("192.168.0.3:2223:123:asd:dsa:3", device);
			}
		};
		
		runnables.add(runnable);
		
		// when 
		boolean ended;
		try {
			MultithreadExecutor.assertConcurrent(runnables,1000);
			ended = true;
		} catch (InterruptedException e) {
			ended = false;
		}
		
		// then
		Assert.assertTrue(ended);
		Assert.assertTrue(ended);
		devices = deviceManager.getConnectedDevices().keySet();
		Assert.assertEquals(4, devices.size());
	}
	
	//@Test
	public void stage20_addMultiStatusToDevicesAndCheckIfAllWereSaved() {
		
		// given
		List<Runnable> runnables = new LinkedList<>();
		
		Runnable runnable = new Runnable() {
			public void run() {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				deviceManager.addDeviceStatus("serial_device_1", status);
			}
		};
		
		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run(){
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				deviceManager.addDeviceStatus("serial_device_1", status);
			}
		};
		
		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run()  {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				deviceManager.addDeviceStatus("serial_device_1", status);
			}
		};
		
		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run() {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				deviceManager.addDeviceStatus("serial_device_2", status);
			}
		};
		
		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run() {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				deviceManager.addDeviceStatus("serial_device_2", status);
			}
		};
		
		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run() {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				deviceManager.addDeviceStatus("serial_device_3", status);
			}
		};
		
		runnables.add(runnable);
		
		runnable = new Runnable() {
			public void run() {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				deviceManager.addDeviceStatus("serial_device_3", status);
			}
		};
		
		runnables.add(runnable);
		
		// when 
		boolean ended;
		try {
			MultithreadExecutor.assertConcurrent(runnables,1000);
			ended = true;
		} catch (InterruptedException e) {
			ended = false;
		}
			
		// then
		Assert.assertTrue(ended);
		Set<Device> devices = deviceManager.getConnectedDevices().keySet();
		Assert.assertEquals(3, devices.size());
		
		List<DeviceStatus> statuses = deviceManager.getDeviceStatuses("serial_device_1", new DateTime(0), new DateTime());
		Assert.assertEquals(3, statuses.size());
		
		statuses = deviceManager.getDeviceStatuses("serial_device_2", new DateTime(0), new DateTime());
		Assert.assertEquals(2, statuses.size());
		
	}
}
