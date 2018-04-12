package com.app.hos.tests.integrations.security;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.persistance.models.User;
import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.filters.AuthenticationFilter;
import com.app.hos.security.servlets.ChallengeServlet;
import com.app.hos.security.servlets.LoginServlet;
import com.app.hos.service.managers.UserManager;
import com.app.hos.utils.embeddedserver.EmbeddedTomcat;
import com.app.hos.utils.json.JsonConverter;
import com.app.hos.utils.security.SecurityUtils;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.apache.catalina.LifecycleException;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmbeddedServletContextIT {

	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private UserManager userManager;
	
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
    public void stage5_makeRequestWithJSSessionIdCookieWhichNotExistsOnServerSideShouldReturnUnauthenticatedSateAndCreateNewSession() 
    																							throws IOException, SAXException {
    	WebConversation client = new WebConversation();
    	String invalidSessionId = SecurityUtils.getRandomAsString();
    	client.addCookie("JSESSIONID", invalidSessionId);
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/");
    	WebResponse response = client.getResponse(request);
    	Assert.assertEquals(200, response.getResponseCode());
    }
    
    @Test
    public void stage10_callDeviceShouldForwardToLoginPageWithNewSessionIfUserIsNotLogin() throws IOException, SAXException {
    	WebConversation client = new WebConversation();
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/devices");
    	WebResponse response = client.getResponse(request);
    	Map<String,String> cookies = SecurityUtils.getCookies(response.getHeaderField("SET-COOKIE"));
    	String sessionId = cookies.get("JSESSIONID");
    	
    	Assert.assertNotNull(sessionId);
    	Assert.assertEquals(200, response.getResponseCode());
    }
    
    @Test
    public void stage20_callLoginShouldReturnLoginPageWithNewSessionIfUserIsNotLogin() throws IOException, SAXException {
    	WebConversation client = new WebConversation();
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	WebResponse response = client.getResponse(request);
    	Map<String,String> cookies = SecurityUtils.getCookies(response.getHeaderField("SET-COOKIE"));
    	String sessionId = cookies.get("JSESSIONID");
    	
    	Assert.assertNotNull(sessionId);
    	Assert.assertEquals(200, response.getResponseCode());
    }
    
    @Test
    public void stage30_callNotChallengeNorLoginAfterChangeStateToAuthenticatingStateShouldReturnUnauthorizedRequestError() 
    																	throws IOException, SAXException {
    	// expected
    	exception.expect(HttpException.class);
    	
    	WebConversation client = new WebConversation();
    	
    	// create request
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	// get response
    	WebResponse response = client.getResponse(request);
    	Map<String,String> cookies = SecurityUtils.getCookies(response.getHeaderField("SET-COOKIE"));
    	String sessionId = cookies.get("JSESSIONID");
    	Assert.assertNotNull(sessionId);
    	
    	// create request
    	request = new GetMethodWebRequest("http://localhost:8080/HOS/device");
    	client.addCookie("JSESSIONID", sessionId);
    	// get response
    	response = client.getResponse(request);
    }
    
    @Test
    public void stage40_callChallengeWithInvalidUserShouldReturn401ErrorCodeWithUserNotFoundMessage() 
    																		throws IOException, SAXException {
    	final String USR_NAME = "Luki";
    	
    	// expected
    	exception.expect(HttpException.class);
        
    	WebConversation client = new WebConversation();
    	
    	// create request
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	
    	// get response
    	WebResponse response = client.getResponse(request);
    	Map<String,String> cookies = SecurityUtils.getCookies(response.getHeaderField("SET-COOKIE"));
    	String sessionId = cookies.get("JSESSIONID");
    	Assert.assertNotNull(sessionId);
    	
    	client.addCookie("JSESSIONID", sessionId);
    	WebForm[] forms = response.getForms();
    	Assert.assertEquals(1, forms.length);
    	
    	// create request
    	WebForm loginForm = forms[0];
    	loginForm.setParameter("user", USR_NAME);
    	
    	// get response
    	response = loginForm.submit();
    }
    
    @Test
    @DirtiesContext
    public void stage50_callLoginWithValidUserChallengeShouldReturn200Status() 
    												throws MalformedURLException, IOException, SAXException {    	
    	final String PASS = "password";
    	User user = new User("Lukasz");
    	user.setPassword(PASS);
    	userManager.addUser(user);

    	WebConversation client = new WebConversation();
    	
    	// create request
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	
    	// get response
    	WebResponse response = client.getResponse(request);
    	Map<String,String> cookies = SecurityUtils.getCookies(response.getHeaderField("SET-COOKIE"));
    	String sessionId = cookies.get("JSESSIONID");
    	Assert.assertNotNull(sessionId);
    	
    	client.addCookie("JSESSIONID", sessionId);
    	WebForm[] forms = response.getForms();
    	Assert.assertEquals(1, forms.length);
    	
    	// create request
    	WebForm loginForm = forms[0];
    	loginForm.setParameter("user", user.getName());
    	response = loginForm.submit();
    	
    	// get response
    	LOG.info(response.getText());
    	Assert.assertEquals("application/json", response.getContentType());
    	String receivedJson = response.getText();
    	UserChallenge userChallenge = JsonConverter.getObject(receivedJson, UserChallenge.class);
    	String oneTimeChallengeResponse = getOneTimeChallengeResponse(userChallenge, PASS);

    	// create request
    	request = new PostMethodWebRequest("http://localhost:8080/HOS/login");
    	request.setParameter("challenge", oneTimeChallengeResponse);

    	// get response
    	response = client.getResponse(request);
    	
    	Assert.assertEquals(200, response.getResponseCode());
    }
    
    @Test
    @DirtiesContext
    public void stage60_callLoginWithInvalidUserChallengeShouldReturnErrorCode() 
    												throws MalformedURLException, IOException, SAXException {   
    	final String PASS = "password";
    	User user = new User("Lukasz");
    	user.setPassword(PASS);
    	userManager.addUser(user);

    	// expected
    	exception.expect(HttpException.class);
    	
    	WebConversation client = new WebConversation();
    	
    	// create request
    	WebRequest request = new GetMethodWebRequest("http://localhost:8080/HOS/login");
    	
    	// get response
    	WebResponse response = client.getResponse(request);
    	Map<String,String> cookies = SecurityUtils.getCookies(response.getHeaderField("SET-COOKIE"));
    	String sessionId = cookies.get("JSESSIONID");
    	Assert.assertNotNull(sessionId);
    	
    	client.addCookie("JSESSIONID", sessionId);
    	WebForm[] forms = response.getForms();
    	Assert.assertEquals(1, forms.length);
    	
    	// create request
    	WebForm loginForm = forms[0];
    	loginForm.setParameter("user", user.getName());
    	response = loginForm.submit();
    	
    	// get response
    	Assert.assertEquals("application/json", response.getContentType());
    	String receivedJson = response.getText();
    	JsonConverter.getObject(receivedJson, UserChallenge.class);
    	String oneTimeChallengeResponse = SecurityUtils.getRandomAsString(); // generate invalid challenge

    	// create request
    	request = new PostMethodWebRequest("http://localhost:8080/HOS/login");
    	request.setParameter("challenge", oneTimeChallengeResponse);

    	// get response
    	response = client.getResponse(request);
    }
    
    private String getOneTimeChallengeResponse (UserChallenge userChallenge, String userPassword) {
    	String receivedSalt = userChallenge.getSalt();
    	String receivedChallenge = userChallenge.getChallenge();
		String hash = SecurityUtils.hash(userPassword + receivedSalt);
    	return SecurityUtils.hash(hash + receivedChallenge);
    }
}
