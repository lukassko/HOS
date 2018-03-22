package com.app.hos.tests.units.security;

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
import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.hos.persistance.models.User;
import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.security.servlets.ChallengeServlet;
import com.app.hos.utils.json.JsonConverter;
import com.app.hos.utils.security.SecurityUtils;

import org.junit.Assert;

// to make integrations tests use: MockMvc and Embedded Tomcat
public class ChallengeServletTest {
	
	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Mock
	private UserDetailsService userDetailsService;

	private ChallengeServlet servlet;
	
	@Before
	public void setUp() throws ServletException {
		MockitoAnnotations.initMocks(this);
		MockServletContext context = new MockServletContext();
		MockServletConfig config = new MockServletConfig(context);
		servlet = new ChallengeServlet(userDetailsService);
		servlet.init(config);
	}

	@Test
	public void servletAfterCorrectAuthenticationShouldRedirectToMain() throws ServletException, IOException {
		// given
		User user = new User("Lukasz");
		String userHash = SecurityUtils.getRandomAsString(24);
		String userSalt = SecurityUtils.getRandomAsString(5);
		user.setHash(userHash);
		user.setSalt(userSalt);
		UserDetails userDetails = new HosUserDetails(user);

		Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setAttribute("user", "Lukasz");
		request.getSession(true);
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		// when 
		servlet.doPost(request, response);
		
		// then
		HttpSession session = request.getSession();
		String challnege = (String)session.getAttribute("challenge");
		UserChallenge userChallenge = new UserChallenge().setSalt(userSalt).setChallenge(challnege);
		String jsonChallenge = JsonConverter.getJson(userChallenge);
		
		Assert.assertEquals(jsonChallenge, response.getContentAsString());
	}

}
