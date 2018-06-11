package com.app.hos.tests.integrations.config;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.CommandRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
public class PostContextLoaderIT {
	
	@Autowired
	private DeviceRepository deviceRepositiry;
	
	@Autowired
	private CommandRepository commandRepository;
	
	@Test
	public void afterContextStartedCommandFactoryShouldAddPossibleDeviceTypesToDb () {
		//given
			// CONTEXT IS LOADED
		
		// when
		DeviceTypeEntity phoneType = deviceRepositiry.findType(DeviceType.PHONE);
		DeviceTypeEntity serverType = deviceRepositiry.findType(DeviceType.SERVER);
		DeviceTypeEntity tvType = deviceRepositiry.findType(DeviceType.TV);
		
		//then
		Assert.assertNotNull(phoneType);
		Assert.assertNotNull(serverType);
		Assert.assertNotNull(tvType);
	}
	
	@Test
	public void afterContextStartedCommandFactoryShouldAddPossibleCommandsTyppeToDb () {
		//given
			// CONTEXT IS LOADED
	
		// when
		CommandTypeEntity getStatusType = commandRepository.find(CommandType.GET_STATUS);
		CommandTypeEntity myStatusType = commandRepository.find(CommandType.MY_STATUS);
		CommandTypeEntity badConversionType = commandRepository.find(CommandType.BAD_CONVERSION);
		CommandTypeEntity helloType = commandRepository.find(CommandType.HELLO);
		
		//then
		Assert.assertNotNull(getStatusType);
		Assert.assertNotNull(myStatusType);
		Assert.assertNotNull(badConversionType);
		Assert.assertNotNull(helloType);
	}
	
	@Test
	public void afterContextStartedCommandFactoryShouldAddPossibleCommandsTyppeWithCorrectDeviceType() {
		//given
			// CONTEXT IS LOADED
	
		// when
		CommandTypeEntity getStatusType = commandRepository.find(CommandType.GET_STATUS);
		CommandTypeEntity myStatusType = commandRepository.find(CommandType.MY_STATUS);
		CommandTypeEntity badConversionType = commandRepository.find(CommandType.BAD_CONVERSION);
		CommandTypeEntity helloType = commandRepository.find(CommandType.HELLO);
		
		//then
		Assert.assertEquals(3, getStatusType.getDevices().size());
		Assert.assertEquals(3, myStatusType.getDevices().size());
		Assert.assertEquals(3, badConversionType.getDevices().size());
		Assert.assertEquals(3, helloType.getDevices().size());
	}
}
