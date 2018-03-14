package com.app.hos.security.states.concretestates;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;

import com.app.hos.security.states.AuthenticationState;
import com.app.hos.security.states.StatesAuthenticator;

public class AuthenticatedState implements AuthenticationState {
	
	private Authentication authentication;

	private boolean isNotInitialize = true;
	
	public AuthenticatedState(Authentication authentication) {
		this.authentication = authentication;
	}
	
	@Override
	public void doAuthentication(StatesAuthenticator authentication,ServletRequest request, 
			ServletResponse response,FilterChain chain) throws ServletException, IOException {
		
		if (isNotInitialize) {
			doInitialize(authentication);
		}
			
		if (isSessionExpired(request)) {
			doSessionExpired(authentication, request, response, chain);
			return;
		}
		
		// check principal, roles and allowed operation
		
		chain.doFilter(request, response);
	}

	private void doInitialize(StatesAuthenticator authentication) {
		authentication.setAuthentication(this.authentication);
		isNotInitialize = false;
	}

	private void doSessionExpired(StatesAuthenticator authentication, ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.setStatus(403); // set status about session expired
		authentication.setState(new UnauthenticatedState());
		authentication.doAuthentication(request, response, chain);
	}
	
	private boolean isSessionExpired(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession(false);
        if (session == null && httpRequest.getRequestedSessionId() != null && !httpRequest.isRequestedSessionIdValid()) 
        	return true;
        return false;
	}

}
