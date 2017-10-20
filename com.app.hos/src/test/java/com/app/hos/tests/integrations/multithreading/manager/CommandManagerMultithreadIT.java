package com.app.hos.tests.integrations.multithreading.manager;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.config.repository.SqlitePersistanceConfig;
import com.app.hos.service.SystemFacade;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.tests.integrations.config.ApplicationContextConfig;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class, ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandManagerMultithreadIT {
	
	@InjectMocks
	@Autowired
	private CommandManager commandManager;
	
	@Mock
	private SystemFacade systemFacade;
	
	
	@Test
	public void stage00_something() {
	}

}
