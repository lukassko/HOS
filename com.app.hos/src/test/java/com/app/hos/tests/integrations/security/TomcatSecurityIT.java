package com.app.hos.tests.integrations.security;

import org.junit.AfterClass;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.WebSecurityConfig;
import com.app.hos.utils.embeddedserver.EmbeddedTomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

//@Ignore("run only one integration test")
//@Profile("web-integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes = {WebSocketConfig.class})
@ContextConfiguration(classes = {ApplicationContextConfig.class, WebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TomcatSecurityIT {

	private static EmbeddedTomcat tomcat;

   
    @BeforeClass
    public static void setupClass() {
        tomcat = new EmbeddedTomcat();
        tomcat.initInstance();

        try {
			tomcat.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
    }
    

      
    @AfterClass
    public static void shutDownTomcat()  {
    	 try {
 			tomcat.stop();
 		} catch (LifecycleException e) {
 			e.printStackTrace();
 		}
    }
    
    @Test
    public void stage00_checkIfEmbeddedTomactHasStarted() throws LifecycleException {
    	System.out.println("WOKRKI");
        if (!tomcat.isStarted()) {
        	throw new LifecycleException();
        }
    }

}
