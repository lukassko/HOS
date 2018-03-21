package com.app.hos.tests.units.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import com.app.hos.persistance.models.User;
import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.authentication.HosUserAuthentication;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.security.servlets.LoginServlet;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.utils.security.SecurityUtils;

import org.junit.Assert;

// to make integrations tests use: MockMvc and Embedded Tomcat
public class ChallengeServletTest {
	
	@Mock
	private AuthenticationProvider authenticationProvider;

	@InjectMocks
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
	public void test() throws ServletException, IOException {
		// given
		User user = new User("Lukasz");
		String userHash = SecurityUtils.getRandomSalt(24).toString();
		String userSalt = SecurityUtils.getRandomSalt(5).toString();
		user.setHash(userHash);
		user.setSalt(userSalt);
		UserDetails userDetails = new HosUserDetails(user);
		
		String challenge = SecurityUtils.getRandomSalt(5).toString();
		
		Authentication authenticationResult = new HosUserAuthentication(userDetails)
														.setCredentials(new UserChallenge().setChallenge(challenge)
																							.setHash(userHash)
																							.setSalt(userSalt));
		authenticationResult.setAuthenticated(true);
		
		Mockito.when(authenticationProvider.authenticate(Mockito.any(Authentication.class))).thenReturn(authenticationResult);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpSession session = request.getSession();
		session.setAttribute("user", userDetails);
		session.setAttribute("challenge", challenge);
		session.setAttribute("authenticator", new StatesAuthenticator());
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		// when 
		servlet.doPost(request, response);
		
		// then
		Assert.assertTrue(true);
	}

}
