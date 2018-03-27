package com.app.hos.tests.units.security.states;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.security.states.concretestates.AuthenticatingState;

public class AuthenticatingStateTest {
	
	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Mock
	private StatesAuthenticator statesAuthenticator;
	
	private AuthenticatingState authenticatingState;

	@Before
	public void setUp() throws ServletException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void authenticatingStateShouldDoFiltersOnChainsIfIsLoggingRequest() throws IOException, ServletException  {
		
		// given
		authenticatingState = new AuthenticatingState();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain chain = Mockito.mock(FilterChain.class);

		request.setContextPath("/HOS");
		request.setRequestURI("/HOS/login");
		
		// when 
		authenticatingState.doAuthentication(statesAuthenticator, request, response, chain);
		
		// then
		Mockito.verify(chain,Mockito.times(1)).doFilter(request, response);
	}

	@Test
	public void authenticatingStateShouldDoFiltersOnChainsIfIsChallengeRequest() throws IOException, ServletException  {
		
		// given
		authenticatingState = new AuthenticatingState();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain chain = Mockito.mock(FilterChain.class);

		request.setContextPath("/HOS");
		request.setRequestURI("/HOS/challenge");
		
		// when 
		authenticatingState.doAuthentication(statesAuthenticator, request, response, chain);
		
		// then
		Mockito.verify(chain,Mockito.times(1)).doFilter(request, response);
	}
	
	@Test
	public void authenticatingStateShouldSendError401WhenRequestUrlIsNotLoginOrChallenge() throws IOException, ServletException  {
		
		// given
		authenticatingState = new AuthenticatingState();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain chain = Mockito.mock(FilterChain.class);

		request.setContextPath("/HOS");
		request.setRequestURI("/HOS/somepath");
		
		// when 
		authenticatingState.doAuthentication(statesAuthenticator, request, response, chain);
		
		// then
		Assert.assertEquals(401,response.getStatus());
		Assert.assertEquals("Unauthorized request",response.getErrorMessage());
	}
}
