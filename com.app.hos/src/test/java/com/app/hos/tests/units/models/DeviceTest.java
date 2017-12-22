package com.app.hos.tests.units.models;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.share.utils.DateTime;

import java.util.Set;

import javax.validation.*;

public class DeviceTest {
	
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	public void compareTwoDevicesShouldReturnTrue() {

		Connection connection= new Connection("192.168.0.12:3456:123:asd:dsa:213", 
				"localhost", "192.168.0.12", 3456, new DateTime());
		
		Device device_1 = new Device("Device1", "123123123");
		Device device_2 = new Device("Device1", "123123123");
		
		device_1.setConnection(connection);
		device_2.setConnection(connection);
		
		Assert.assertEquals(device_1, device_2);
	}
	
	@Test
	public void compareTwoDevicesShouldReturnFalse() {
		Connection connection= new Connection("192.168.0.12:3456:123:asd:dsa:233", 
				"localhost", "192.168.0.12", 3456, new DateTime());
		
		Device device_1 = new Device("Device1", "123123123");
		Device device_2 = new Device("Device2", "123123123");
		
		device_1.setConnection(connection);
		device_2.setConnection(connection);
		
		Assert.assertNotEquals(device_1, device_2);
	}
	
	@Test
	public void testHibernateValidation() {
		//target.setName(null);
		Device device = new Device("Name","");
        Set<ConstraintViolation<Device>> constraintViolations = validator.validate(device);
        Assert.assertEquals(constraintViolations.size(), 1);
	}
	
}
