package com.app.hos.tests.integrations.security;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.security.filters.AuthenticationFilter;
import com.app.hos.security.servlets.ChallengeServlet;
import com.app.hos.security.servlets.LoginServlet;
import com.app.hos.utils.embeddedserver.EmbeddedTomcat;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.catalina.LifecycleException;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmbeddedServletContextIT {

	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private static EmbeddedTomcat tomcat;
	
    @BeforeClass
    public static void setupAndStartTomcat() throws Exception {
        tomcat = new EmbeddedTomcat();
        tomcat.initInstance();
        tomcat.addServlet(LoginServlet.class);
        //tomcat.addServlet(ChallengeServlet.class);
        tomcat.addFilter(AuthenticationFilter.class);
		tomcat.start();
    }
    
    @AfterClass
    public static void shutDownTomcat() throws LifecycleException  {
 		tomcat.stop();
    }
    
    @Test
    public void stage00_checkIfEmbeddedTomactHasStarted()  {
        Assert.assertTrue(tomcat.isStarted());
    }

    @Test
    public void stage10_testHttpUnit() throws IOException, SAXException {
    	System.out.println("stage10_testHttpUnit START!");
    	WebConversation wc = new WebConversation();
    	WebResponse resp = wc.getResponse("http://localhost:8080/HOS/devices");
    	LOG.info(Integer.toString(resp.getResponseCode()));
    	LOG.info(resp.getText());
    }
    
    @Test
    public void stage20_testOwnServlet() throws IOException, SAXException {
    	System.out.println("stage20_testOwnServlet START!");
    	WebConversation wc = new WebConversation();
    	WebResponse resp = wc.getResponse("http://localhost:8080/HOS/login");
    	LOG.info(Integer.toString(resp.getResponseCode()));
    	LOG.info(resp.getText());
    }
}
