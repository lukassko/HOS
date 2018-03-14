package com.app.hos.security.states.concretestates;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.app.hos.security.states.AuthenticationState;
import com.app.hos.security.states.StatesAuthenticator;

public class InvalidAuthenticationState implements AuthenticationState {

	@Override
	public void doAuthentication(StatesAuthenticator authentication,ServletRequest request, 
			ServletResponse response,FilterChain chain) throws IOException, ServletException {
		
		authentication.setAuthentication(null);
		
	}

}
