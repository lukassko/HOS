package com.app.hos.tests.integrations.aspect;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.tests.utils.Thrower;
import com.app.hos.utils.exceptions.NotExecutableCommandException;
import com.app.hos.utils.exceptions.handler.ExceptionUtils;


//@Ignore("run only one integration test")
@WebAppConfiguration 
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExcpetionAspectIT {
	

	private Thrower thrower = new Thrower();
	
	@Autowired
	private LoggingRepository loggingRepository;
	
	@Test
	public void stage10_checkIfLogWillOccureInDbBeforeExceptionHandling() {	
		
		try {
			thrower.throwNotExeccutableCommand();
		} catch(NotExecutableCommandException e) {
			ExceptionUtils.handle(e);
		}
		
		//Assert.assertTrue(loggingRepository.findAll().size() == 1 );
	}
	
	
}

    

