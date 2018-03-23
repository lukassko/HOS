package com.app.hos.tests.units.security.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import com.app.hos.persistance.models.User;
import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.authentication.HosUserAuthentication;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.security.servlets.LoginServlet;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.security.states.concretestates.AuthenticatingState;
import com.app.hos.utils.security.SecurityUtils;

import org.junit.Assert;

// to make integrations tests use: MockMvc and Embedded Tomcat
public class LoginServletTest {
	
	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Mock
	private AuthenticationProvider authenticationProvider;

	private LoginServlet servlet;
	
	@Before
	public void setUp() throws ServletException {
		MockitoAnnotations.initMocks(this);
		MockServletContext context = new MockServletContext();
		MockServletConfig config = new MockServletConfig(context);
		servlet = new LoginServlet(authenticationProvider);
		servlet.init(config);
	}

	@Test
	public void servletAfterCorrectAuthenticationShouldRedirectToMain() throws ServletException, IOException {
		// given
		User user = new User("Lukasz");
		String userHash = SecurityUtils.getRandomAsString();
		String userSalt = SecurityUtils.getRandomAsString();
		user.setHash(userHash);
		user.setSalt(userSalt);
		UserDetails userDetails = new HosUserDetails(user);
		
		String challenge = SecurityUtils.getRandomAsString();
		
		Authentication authenticationResult = new HosUserAuthentication(userDetails)
														.setCredentials(new UserChallenge().setChallenge(challenge)
																							.setHash(userHash)
																							.setSalt(userSalt));
		authenticationResult.setAuthenticated(true);
		
		StatesAuthenticator statesAuthenticator = new StatesAuthenticator();
		statesAuthenticator.setState(new AuthenticatingState());
		
		Mockito.when(authenticationProvider.authenticate(Mockito.any(Authentication.class))).thenReturn(authenticationResult);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpSession session = request.getSession();
		session.setAttribute("user", userDetails);
		session.setAttribute("challenge", challenge);

		session.setAttribute("authenticator", statesAuthenticator);
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		// when 
		servlet.doPost(request, response);
		
		// then
		Assert.assertTrue(response.getRedirectedUrl().equals("/"));
	}

	@Test
	public void servletAfterIncorrectAuthenticationShouldSend401Error() throws ServletException, IOException {
		// given
		User user = new User("Lukasz");
		String userHash = SecurityUtils.getRandom(24).toString();
		String userSalt = SecurityUtils.getRandom(5).toString();
		user.setHash(userHash);
		user.setSalt(userSalt);
		UserDetails userDetails = new HosUserDetails(user);
		
		String challenge = SecurityUtils.getRandom(5).toString();
		
		Authentication authenticationResult = new HosUserAuthentication(userDetails)
														.setCredentials(new UserChallenge().setChallenge(challenge)
																							.setHash(userHash)
																							.setSalt(userSalt));
		authenticationResult.setAuthenticated(true);
		
		StatesAuthenticator statesAuthenticator = new StatesAuthenticator();
		statesAuthenticator.setState(new AuthenticatingState());
		
		Mockito.when(authenticationProvider.authenticate(Mockito.any(Authentication.class)))
											.thenThrow(new BadCredentialsException("Invalid user password"));
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpSession session = request.getSession();
		session.setAttribute("user", userDetails);
		session.setAttribute("challenge", challenge);

		session.setAttribute("authenticator", statesAuthenticator);
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		// when 
		servlet.doPost(request, response);

		// then
		Assert.assertEquals(401,response.getStatus());
		Assert.assertEquals("Invalid user password",response.getErrorMessage());
	}
}
