package com.app.hos.tests.integrations.multithreading.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.junit.Assert;
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
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.ReflectionUtils;
import com.app.hos.utils.Utils;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceManagerMultithreadIT {
	
	@Autowired
	private DeviceManager manager;
	
	private static final double DELTA = 1e-15;
	
	@Test
	public void stage10_connectMultipleDeviceAndCheckIfAllWasSaved() {

		List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
		
		Callable<Void> callable = new Callable<Void>() {
			public Void call() throws Exception {
				NewDevice device = new NewDevice("serial", "name", DeviceType.SERVER, "192:168:0:1", 2222);
				manager.openDeviceConnection("192.168.0.1:1111:123:asd:dsa:213", device);
				return null;
			}
		};

		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				NewDevice device = new NewDevice("serial", "name", DeviceType.SERVER, "192:168:0:1", 2222);
				manager.openDeviceConnection("192.168.0.1:1111:123:asd:dsa:213", device);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				NewDevice device = new NewDevice("serial", "name", DeviceType.SERVER, "192:168:0:1", 2222);
				manager.openDeviceConnection("192.168.0.1:1111:123:asd:dsa:213", device);
				return null;
			}
		};
		
		callables.add(callable);
		
		try {
			com.app.hos.tests.utils.Utils.assertConcurrent(callables,1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<Device> devices = manager.getConnectedDevices().keySet();
		Assert.assertEquals(3, devices.size());
	}
	
	@Test
	public void stage20_addMultiStatusToDevicesAndCheckIfAllWereSaved() {
		List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
		
		Callable<Void> callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				manager.addDeviceStatus("serial_device_1", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				manager.addDeviceStatus("serial_device_1", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				manager.addDeviceStatus("serial_device_1", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				manager.addDeviceStatus("serial_device_2", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				manager.addDeviceStatus("serial_device_2", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				manager.addDeviceStatus("serial_device_3", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), Utils.generateRandomDouble(), Utils.generateRandomDouble());
				manager.addDeviceStatus("serial_device_3", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		try {
			com.app.hos.tests.utils.Utils.assertConcurrent(callables,1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Set<Device> devices = manager.getConnectedDevices().keySet();
		Assert.assertEquals(3, devices.size());
		
		List<DeviceStatus> statuses = manager.getDeviceStatuses("serial_device_1", new DateTime(0), new DateTime());
		Assert.assertEquals(3, statuses.size());
		
		statuses = manager.getDeviceStatuses("serial_device_2", new DateTime(0), new DateTime());
		Assert.assertEquals(2, statuses.size());
		
	}
	
	@Test
	public void stage30_connectDeviceAndAddStatusToOthersShouldAddDatatoDb() {
		
		try {
			Thread.sleep(1000); // wait some time to increase DateTime in database for new DeviceStatus
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
		
		List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
		
		Callable<Void> callable = new Callable<Void>() {
			public Void call() throws Exception {
				DeviceStatus status = new DeviceStatus(new DateTime(), 13.12, 65.23);
				manager.addDeviceStatus("serial_device_2", status);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				NewDevice device = new NewDevice("serial", "name", DeviceType.SERVER, "192:168:0:1", 2222);
				manager.openDeviceConnection("192.168.0.1:1111:123:asd:dsa:213", device);
				return null;
			}
		};
		
		callables.add(callable);
		
		try {
			com.app.hos.tests.utils.Utils.assertConcurrent(callables,1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Map<Device,DeviceStatus> deviceStatuses = manager.getConnectedDevices();
		Set<Device> devices = deviceStatuses.keySet();
		Assert.assertEquals(4, devices.size());
		
		Device device = null;
		for (Device dev : devices) 
			if (dev.getSerial().equals("serial_device_2"))
				device =  dev;
		Assert.assertNotNull(device);
		DeviceStatus status = deviceStatuses.get(device);
		Assert.assertNotNull(status);
		Assert.assertEquals(13.12, status.getRamUsage(),DELTA);
		Assert.assertEquals(65.23, status.getCpuUsage(),DELTA);
		
	}

}
