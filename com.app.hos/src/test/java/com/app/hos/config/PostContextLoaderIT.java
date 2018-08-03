package com.app.hos.config;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.init.PostContextLoader;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.CommandRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.command.builder_v2.CommandFactory;
import com.app.hos.service.command.future.FutureCommandFactory;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.command.type.DeviceType;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerFactory;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;
import com.app.hos.utils.ApplicationContextProvider;

@Ignore("run only one integration test")
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
@ActiveProfiles("integration-test")
@Transactional
public class PostContextLoaderIT {
	
	@Autowired
	private DeviceRepository deviceRepositiry;
	
	@Autowired
	private CommandRepository commandRepository;
	
	// TODO: test ExceptionHandlerFactory FutureWebCommandFactory FutureCommandFactory
	// Already tested: CommandFactory
	
	@Test
	public void afterContextStartedCommandFactoryShouldAddPossibleDeviceTypesToDb () {
		//given
			// CONTEXT IS LOADED
		
		// when
		DeviceTypeEntity phoneType = deviceRepositiry.findType(DeviceType.PHONE);
		DeviceTypeEntity serverType = deviceRepositiry.findType(DeviceType.SERVER);
		DeviceTypeEntity tvType = deviceRepositiry.findType(DeviceType.TV);
		List<DeviceTypeEntity> allTypes = deviceRepositiry.findAllTypes();
		
		//then
		Assert.assertNotNull(phoneType);
		Assert.assertNotNull(serverType);
		Assert.assertNotNull(tvType);
		Assert.assertEquals(3, allTypes.size());
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
