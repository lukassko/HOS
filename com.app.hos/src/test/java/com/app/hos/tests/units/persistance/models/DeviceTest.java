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

		Connection connection= new Connection("192.168.0.12:3456:123:asd:dsa:213", 
				"localhost", "192.168.0.12", 3456, new DateTime());
		
		Device device_1 = new Device("Device1", "123123123",new DeviceTypeEntity(DeviceType.PHONE));
		Device device_2 = new Device("Device1", "123123123",new DeviceTypeEntity(DeviceType.PHONE));
		
		device_1.setConnection(connection);
		device_2.setConnection(connection);
		
		Assert.assertEquals(device_1, device_2);
	}
	
	@Test
	public void compareTwoDevicesShouldReturnFalse() {
		Connection connection= new Connection("192.168.0.12:3456:123:asd:dsa:233", 
				"localhost", "192.168.0.12", 3456, new DateTime());
		
		Device device_1 = new Device("Device1", "123123123",new DeviceTypeEntity(DeviceType.PHONE));
		Device device_2 = new Device("Device2", "123123123",new DeviceTypeEntity(DeviceType.PHONE));
		
		device_1.setConnection(connection);
		device_2.setConnection(connection);
		
		Assert.assertNotEquals(device_1, device_2);
	}
	
	@Test
	public void testHibernateValidation() {
		//target.setName(null);
		Device device = new Device("Name","",new DeviceTypeEntity(DeviceType.PHONE));
        Set<ConstraintViolation<Device>> constraintViolations = validator.validate(device);
        Assert.assertEquals(constraintViolations.size(), 1);
	}
	
}
