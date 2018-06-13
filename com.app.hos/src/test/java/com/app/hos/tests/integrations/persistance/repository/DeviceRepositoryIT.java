package com.app.hos.tests.integrations.persistance.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.init.PostContextLoader;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerFactory;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;
import com.app.hos.share.command.builder_v2.CommandFactory;
import com.app.hos.share.command.future.FutureCommandFactory;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.ApplicationContextProvider;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.IsCollectionContaining.*;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = {
				ApplicationContextProvider.class,
				MysqlPersistanceConfig.class,
				PostContextLoader.class,
				CommandFactory.class,
				HOSExceptionHandlerFactory.class,
				FutureWebCommandFactory.class,
				FutureCommandFactory.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class DeviceRepositoryIT {

	@Autowired
    private DeviceRepository deviceRepository;
	
	@Test(expected=ConstraintViolationException.class)
	public void test00_saveDeviceWithNullAsSerialFieldShouldThrowException() {
		// given
		DeviceTypeEntity type = deviceRepository.findType(DeviceType.PHONE);

		Device device = new Device("device_0",null, type);
		device.setConnection(null);
		
		// when
		deviceRepository.save(device);
		
		// then
			// expected exception
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void test01_saveDeviceWithEmptySerialFieldShouldThrowException() {
		// given
		DeviceTypeEntity type = deviceRepository.findType(DeviceType.PHONE);

		Device device = new Device("device_1","", type);
		device.setConnection(null);
		
		// when
		deviceRepository.save(device);
		
		// then
			// expected exception
	}
	
	// testing connection
	@Test(expected=ConstraintViolationException.class)
	public void test02_saveDeviceWithConnectionWithNullConnectionIdFieldShouldThrowException() {
		// given
		DeviceTypeEntity typeEntity = deviceRepository.findType(DeviceType.PHONE);
		Device device = new Device("device_2", "serial_2", typeEntity);
		Connection connection = new Connection.Builder()
												.connectionId(null)
												.connectionTime(new DateTime())
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.build();
		device.setConnection(connection);
		
		// when
		deviceRepository.save(device);
		
		// then
			// expected exception
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void test03_saveDeviceWithConnectionWithEmptyConnectionIdFieldShouldThrowException() {
		// given
		DeviceTypeEntity typeEntity = deviceRepository.findType(DeviceType.PHONE);
		Device device = new Device("device_3", "serial_3", typeEntity);
		Connection connection = new Connection.Builder()
												.connectionId("")
												.connectionTime(new DateTime())
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.build();
		device.setConnection(connection);
		
		// when
		deviceRepository.save(device);
		
		// then
			// expected exception
	}
	
	@Test(expected=PersistenceException.class)
	public void test04_saveDeviceWithConnectionWithNullConnectionTimeFieldShouldThrowException() {
		// given
		DeviceTypeEntity typeEntity = deviceRepository.findType(DeviceType.PHONE);
		Device device = new Device("device_4", "serial_4", typeEntity);
		Connection connection = new Connection.Builder()
												.connectionId("connection_id")
												.connectionTime(null)
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.build();
		device.setConnection(connection);
		
		// when
		deviceRepository.save(device);
		
		// then
			// expected exception
	}
	
	@Test
	public void test05_saveDeviceWithNullAsConnectionFieldAddEntityToDb() {
		// given
		String serial = "serial_5";
		DeviceTypeEntity type = deviceRepository.findType(DeviceType.PHONE);

		Device device = new Device("device_05",serial, type);
		device.setConnection(null);
		
		// when
		deviceRepository.save(device);
		Device foundBySerial = deviceRepository.find(serial);
		List<Device> foundAll = deviceRepository.findAll();
		
		// then
		Assert.assertNotNull(foundBySerial);
		Assert.assertEquals(device,foundBySerial);
		Assert.assertTrue(foundAll.size() == 1);
	}
	
	@Test
	public void test10_saveDeviceWithValidConnectionFieldShouldInsertEntityToDb() {
		// given
		String serial = "serial_10";
		String name = "device_10";
		DeviceTypeEntity typeEntity = deviceRepository.findType(DeviceType.PHONE);
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
		
			// these select are not from cache		
		Device foundBySerial = deviceRepository.find(serial);
		List<Device> foundAll = deviceRepository.findAll();
		
		// then
		Assert.assertNotNull(foundBySerial);
		Assert.assertTrue(foundAll.size() == 2);
		Assert.assertThat(foundAll, hasItem(device));
		Assert.assertEquals(name,foundBySerial.getName());
		Assert.assertEquals(typeEntity,foundBySerial.getDeviceType());
		Assert.assertEquals(connection,foundBySerial.getConnection());
	}
	
	@Test
	public void test20_removeDeviceShouldRemoveEntityFromDb() {
		// given
		DeviceTypeEntity type = deviceRepository.findType(DeviceType.PHONE);

		Device device = new Device("device_20","serial_20", type);
		device.setConnection(null);
		deviceRepository.save(device);
		
		// when
		deviceRepository.remove(device);
		List<Device> foundAll = deviceRepository.findAll();
		
		// then
		Assert.assertTrue(foundAll.size() == 2);
		Assert.assertThat(foundAll, not(hasItem(device)));
	}
	
	@Test
	public void test30_findNonExistingDeviceShouldReturnNull() {
		// given
		// when
		Device foundBySerial = deviceRepository.find("non_existsing_serial");
		
		// then
		Assert.assertNull(foundBySerial);
	}
}
