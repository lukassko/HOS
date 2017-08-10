package com.app.hos.tests.integrations.manager;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.AspectConfig;
import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;
import com.app.hos.tests.integrations.config.PersistanceConfig;


@Ignore("run only one integration test")
@ActiveProfiles("integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class , AspectConfig.class, ApplicationContextConfig.class})
public class WebCommandManagerIT {

	
	//@Before
	public static void prepareDataForTests() {
	}
	
	@Test
	public void getAllDevicesWhenNoConnectedDeviceTest() {
		WebCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
	}

}
