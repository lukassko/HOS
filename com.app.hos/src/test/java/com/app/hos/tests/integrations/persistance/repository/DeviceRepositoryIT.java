package com.app.hos.tests.integrations.persistance.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.share.command.type.DeviceType;

import java.util.List;

import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class DeviceRepositoryIT {

	@Autowired
    private DeviceRepository deviceRepository;
	
	@Test
	public void saveDeviceWithNullAsConnectionFieldShouldThrowException() {
		// given
		DeviceTypeEntity type = deviceRepository.findType(DeviceType.PHONE);
		Device device = new Device("device_1", "serial_1", type);
		device.setConnection(null);
		
		// when
		deviceRepository.save(device);
		
		// then
			// expected exception
	}
	
	@Test
	public void saveDeviceWithValidConnectionFieldShouldInsertEntityToDb() {
		// given
		String serial = "serial_1";
		String name = "device_1";
		DeviceType type = DeviceType.PHONE;
		DeviceTypeEntity typeEntity = deviceRepository.findType(type);
		Device device = new Device(name, serial, typeEntity);
		Connection connection = new Connection.Builder()
												.connectionId("connection_id")
												.connectionTime(new DateTime())
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.build();
		device.setConnection(connection);
		
		// when
		deviceRepository.save(device);
		
			// TODO try to clear cache in some way
		
			// these select are not from cache
		Device foundBySerial = deviceRepository.find(serial);
		List<Device> foundAll = deviceRepository.findAll();
		
		// then
		Assert.assertNotNull(foundBySerial);
		Assert.assertTrue(foundAll.size() > 0);
		Assert.assertEquals(name,foundBySerial.getName());
		Assert.assertEquals(typeEntity,foundBySerial.getDeviceType());
	}
}
