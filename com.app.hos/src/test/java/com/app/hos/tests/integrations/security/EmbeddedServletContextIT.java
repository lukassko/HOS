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
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import java.io.IOException;
import java.util.logging.Logger;

import javax.validation.ReportAsSingleViolation;

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
        tomcat.addServlet(ChallengeServlet.class);
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
    public void stage10_callDeviceShouldForwardToLoginPageWithNewSessionIfUserIsNotLogin() throws IOException, SAXException {
    	WebConversation client = new WebConversation();
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/devices");
    	WebResponse response = client.getResponse(request);
    	String sessionId = getSessionIdCookie(response);
    	Assert.assertEquals(200, response.getResponseCode());
    	Assert.assertTrue(sessionId.length() > 0);
    }
    
    @Test
    public void stage20_callLoginShouldReturnLoginPageWithNewSessionIfUserIsNotLogin() throws IOException, SAXException {
    	WebConversation client = new WebConversation();
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	WebResponse response = client.getResponse(request);
    	String sessionId = getSessionIdCookie(response);
    	Assert.assertEquals(200, response.getResponseCode());
    	Assert.assertTrue(sessionId.length() > 0);
    }
    
    @Test
    public void stage30_callNotChallengeNorLoginAfterChangeStateToUnauthenticatedStateShouldReturnUnauthorizedRequestError() 
    																	throws IOException, SAXException {
    	System.out.println("stage30");
    	WebConversation client = new WebConversation();
    	
    	// send request
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	// get response
    	WebResponse response = client.getResponse(request);
    	String sessionId = getSessionIdCookie(response);
    	
    	// send request
    	request = new GetMethodWebRequest("http://localhost:8080/HOS/device");
    	client.addCookie("JSESSIONID", sessionId);
    	// get response
    	response = client.getResponse(request);
    	System.out.println(response.getResponseCode());
    	Assert.assertEquals(200, response.getResponseCode());
    }
    
    @Test
    public void stage40_callChallengeWithInvalidUserShouldReturn401ErrorCodeWithUserNotDoundMessage() 
    																		throws IOException, SAXException {
    	System.out.println("stage40");
    	WebConversation client = new WebConversation();
    	
    	// send request
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	
    	// get response
    	WebResponse response = client.getResponse(request);
    	String sessionId = getSessionIdCookie(response);
    	client.addCookie("JSESSIONID", sessionId);
    	WebForm[] forms = response.getForms();
    	Assert.assertEquals(1, forms.length);
    	
    	// send request
    	WebForm loginForm = forms[0];
    	loginForm.setParameter("user", "Luki");

    	// get response
    	response = loginForm.submit();
    	
    	LOG.info(response.getText());
    	Assert.assertEquals(200, response.getResponseCode());

    }
    
    private String getSessionIdCookie(WebResponse response) {
    	String cookies = response.getHeaderField("SET-COOKIE");
    	int begin = cookies.indexOf("JSESSIONID=");
        int end = cookies.indexOf(";", begin);
    	return cookies.substring(begin+11, end);
    }
}
