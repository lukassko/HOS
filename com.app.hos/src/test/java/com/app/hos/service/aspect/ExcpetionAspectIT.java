package com.app.hos.service.aspect;

import java.util.logging.Level;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.common.Thrower;
import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.config.repository.SqlitePersistanceConfig;
import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.exceptions.handler.ExceptionUtils;


@Ignore("run only one integration test")
@WebAppConfiguration 
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class, ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExcpetionAspectIT {
	
	private Thrower thrower = new Thrower();
	
	@Autowired
	private LoggingRepository loggingRepository;
	
	@Test
	public void stage10_checkIfLogWillOccureInDbBeforeExceptionHandlingForNotExeccutableCommand() {	
		
		try {
			thrower.throwNotExeccutableCommandException();
		} catch(NotExecutableCommandException e) {
			ExceptionUtils.handle(e);
		}
		
		Assert.assertTrue(loggingRepository.findLogForLevel(Level.WARNING).size() == 1);
	}
	
	@Test
	public void stage20_checkIfLogWillOccureInDbBeforeExceptionHandlingForNotExeccutableCommand() {	
		
		try {
			Connection connection = new Connection.Builder().connectionId("192.168.0.22:23452-09:oa9:sd2")
															.hostname("localhost2")
															.ip("192.168.0.22")
															.remotePort(23452)
															.connectionTime(new DateTime())
															.build();
																	
			thrower.throwHistoryConnectionException(connection);
		} catch(HistoryConnectionException e) {
			ExceptionUtils.handle(e);
		}
		
		Assert.assertTrue(loggingRepository.findLogForLevel(Level.WARNING).size() == 2);
	}
	
	@Test
	public void stage30_throwChildExceptionButCatchParentExceptionAndCheckLogs() {
		try {
			thrower.throwJsonException();
		} catch(Exception e) {
			ExceptionUtils.handle(e);
		}
		Assert.assertTrue(loggingRepository.findLogForLevel(Level.WARNING).size() == 3);
	}
		
}

    

