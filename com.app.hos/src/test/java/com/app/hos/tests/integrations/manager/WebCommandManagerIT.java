package com.app.hos.tests.integrations.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;

//@Ignore("run only one integration test")
@ActiveProfiles("integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class,ApplicationContextConfig.class})
public class WebCommandManagerIT {

	@Autowired
	private WebCommandFactory webCommandFactory;
	
	
	@Test
	public void getAllDevicesWhenNoConnectedDeviceTest() {
		//WebCommand command = webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		WebCommand command = webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		//WebCommand command = (WebCommand) appContext.getBean("getCommand", WebCommandType.GET_ALL_DEVICES);
		System.out.println(command.toString());
	}

}
