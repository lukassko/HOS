package com.app.hos.security.states.concretestates;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;

import com.app.hos.security.states.AuthenticationState;
import com.app.hos.security.states.StatesAuthenticator;

public class AuthenticatedState implements AuthenticationState {
	
	private Authentication authentication;

	public AuthenticatedState(Authentication authentication) {
		this.authentication = authentication;
	}
	
	@Override
	public void doAuthentication(StatesAuthenticator authentication,ServletRequest request, 
			ServletResponse response,FilterChain chain) throws ServletException {
		
		authentication.setAuthentication(this.authentication);
		// check principal , roles and allowed operation
	}

}
