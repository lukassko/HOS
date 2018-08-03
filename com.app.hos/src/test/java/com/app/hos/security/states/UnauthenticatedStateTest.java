package com.app.hos.security.states;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.security.states.concretestates.UnauthenticatedState;

import org.junit.Assert;

public class UnauthenticatedStateTest {
	
	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Mock
	private StatesAuthenticator statesAuthenticator;
	
	private UnauthenticatedState unauthenticatedState;

	@Before
	public void setUp() throws ServletException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void unauthenticatedStateShouldRedirectToLoginPageAndCreateNewSession() throws IOException, ServletException  {
		
		// given
		unauthenticatedState = new UnauthenticatedState();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		HttpSession session = request.getSession(false);
		Assert.assertNull(session);
		
		// when 
		unauthenticatedState.doAuthentication(statesAuthenticator, request, response, chain);
		
		// then
		session = request.getSession(false);
		Assert.assertEquals("login", response.getRedirectedUrl());
		Assert.assertNotNull(session);
	}
	
	@Test
	public void unauthenticatedStateShouldInavlidCurrentSessionAndCreateNewSession() throws IOException, ServletException  {
		
		// given
		unauthenticatedState = new UnauthenticatedState();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		HttpSession session = request.getSession(true);
		session.setAttribute("test", "value");
		
		Assert.assertNotNull(session);
		Assert.assertEquals("value",session.getAttribute("test"));
		
		// when 
		unauthenticatedState.doAuthentication(statesAuthenticator, request, response, chain);
		
		// then
		session = request.getSession(false);
		Assert.assertNotNull(session);
		Assert.assertNull(session.getAttribute("test"));
	}
}
