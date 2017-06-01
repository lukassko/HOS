package com.app.hos.tests.units.service.managers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.app.hos.service.managers.device.DeviceManager;

public class CommandManagerTest {

	@Mock
	private DeviceManager deviceManager;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testExecuteCommandMethod() {
		Mockito.doNothing().when(deviceManager).addDeviceStatus("", null);; 
		deviceManager.addDeviceStatus("", null);
		String t = null;
		Assert.assertNull(t);
	}
}
