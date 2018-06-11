package com.app.hos.tests.units.persistance.models;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.share.command.type.DeviceType;

public class DeviceTypeEntityTest {

	@Test
	public void testEqueslOfDeviceTypeEntityShouldReturnTrue() {
		// given
		DeviceTypeEntity type1 = new DeviceTypeEntity(DeviceType.PHONE);
		DeviceTypeEntity type2 = new DeviceTypeEntity(DeviceType.PHONE);
		
		// when then
		Assert.assertTrue(type1.equals(type2));
	}
	
	
	@Test
	public void testEqueslOfDeviceTypeEntityShouldReturnFalse() {
		// given
		DeviceTypeEntity type1 = new DeviceTypeEntity(DeviceType.PHONE);
		DeviceTypeEntity type2 = new DeviceTypeEntity(DeviceType.SERVER);
				
		// when then
		Assert.assertFalse(type1.equals(type2));
	}
	
}
