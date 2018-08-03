package com.app.hos.security.states;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.security.core.Authentication;

import com.app.hos.persistance.models.user.User;
import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.authentication.HosUserAuthentication;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.security.states.concretestates.AuthenticatedState;
import com.app.hos.security.states.concretestates.AuthenticatingState;
import com.app.hos.utils.security.SecurityUtils;

public class AuthenticatedStateTest {
	
	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Mock
	private StatesAuthenticator statesAuthenticator;
	
	private AuthenticatedState authenticatedState;

	@Before
	public void setUp() throws ServletException {
		MockitoAnnotations.initMocks(this);
		authenticatedState = new AuthenticatedState(null);
	}

	@Test
	public void authenticatedStateShouldRedirectToLoginPageIfSessionExpired() throws IOException, ServletException  {
		
		// given
		final String RESOURCE = "WEB-INF/views/users/login.jsp";
				
		RequestDispatcher dispatcher =  Mockito.mock(RequestDispatcher.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		FilterChain chain = Mockito.mock(FilterChain.class);
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		Mockito.when(request.getRequestDispatcher(RESOURCE)).thenReturn(dispatcher);
		
		// RequestedSessionId is a session received from client side (in cookie)
		Mockito.when(request.getRequestedSessionId()).thenReturn("EXAMPLE_EXPIRED_SESSION_ID");
		
		HttpSession session = request.getSession(false);
		Assert.assertNull(session);

		// when 
		authenticatedState.doAuthentication(statesAuthenticator, request, response, chain);
		
		// then
		Mockito.verify(dispatcher,Mockito.times(1)).forward(request, response);
		Mockito.verify(chain,Mockito.times(0)).doFilter(request, response);
	}

	@Test
	public void authenticatedStateShouldRedirectToLoginPageIfIsLogoutRequest() throws IOException, ServletException  {
		
		// given
		final String RESOURCE = "WEB-INF/views/users/login.jsp";

		RequestDispatcher dispatcher =  Mockito.mock(RequestDispatcher.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		FilterChain chain = Mockito.mock(FilterChain.class);
		
		MockHttpServletResponse response = new MockHttpServletResponse();

		Mockito.when(request.getRequestDispatcher(RESOURCE)).thenReturn(dispatcher);
		Mockito.when(request.getRequestURI()).thenReturn("/HOS/logout");
		Mockito.when(request.getContextPath()).thenReturn("/HOS");
		Mockito.when(request.getSession(false)).thenReturn(session);
		
		// when 
		authenticatedState.doAuthentication(statesAuthenticator, request, response, chain);
		
		// then
		Mockito.verify(session,Mockito.times(1)).invalidate();
		Mockito.verify(dispatcher,Mockito.times(1)).forward(request, response);
		Mockito.verify(chain,Mockito.times(0)).doFilter(request, response);
	}
	
	@Test
	public void authenticatedStateShouldDoFiltersOnChainsIfIsNotLogoutRequestAndSessionIsActive() throws ServletException, IOException {
		// given
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain chain = Mockito.mock(FilterChain.class);
		
		request.setContextPath("/HOS");
		request.setRequestURI("/devices");
		request.setRequestedSessionIdValid(true);
		
		// when 
		authenticatedState.doAuthentication(statesAuthenticator, request, response, chain);
				
		// then
		Mockito.verify(chain,Mockito.times(1)).doFilter(request, response);
	}
	
	
}
