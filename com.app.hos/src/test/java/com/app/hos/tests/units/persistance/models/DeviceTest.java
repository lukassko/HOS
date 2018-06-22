package com.app.hos.tests.units.persistance.models;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.share.command.type.DeviceType;

import java.util.Set;

import javax.validation.*;

public class DeviceTest {
	
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	public void compareTwoDevicesShouldReturnTrue() {
		
		//given
		String name = "device";
		String serial = "serial";

		Device device_1 = new Device(name, serial,new DeviceTypeEntity(DeviceType.PHONE));
		Device device_2 = new Device(name, serial,new DeviceTypeEntity(DeviceType.PHONE));
		
		Connection connection = new Connection.Builder()
												.connectionId("connection_id")
												.connectionTime(new DateTime())
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(null)
												.build();
		

		
		device_1.setConnection(connection);
		device_2.setConnection(connection);
		
		// when 
		// then
		Assert.assertEquals(device_1, device_2);
	}
	
	@Test
	public void compareTwoDevicesShouldReturnFalse() {
		
		//given
		String serial = "serial";
		Connection connection = new Connection.Builder()
										.connectionId("connection_id")
										.connectionTime(new DateTime())
										.hostname("hostname")
										.ip("192.168.0.21")
										.remotePort(1234)
										.device(null)
										.build();
			
		Device device_1 = new Device("name1", serial,new DeviceTypeEntity(DeviceType.PHONE));
		Device device_2 = new Device("name2", serial,new DeviceTypeEntity(DeviceType.PHONE));
		
		device_1.setConnection(connection);
		device_2.setConnection(connection);
		
		// when 
		// then
		Assert.assertNotEquals(device_1, device_2);
	}
	
	@Test
	public void testHibernateValidation() {
		// given
		Device device = new Device("name","",new DeviceTypeEntity(DeviceType.PHONE));
		// when
		Set<ConstraintViolation<Device>> constraintViolations = validator.validate(device);
		// then
        Assert.assertEquals(constraintViolations.size(), 1);
	}
	
}
