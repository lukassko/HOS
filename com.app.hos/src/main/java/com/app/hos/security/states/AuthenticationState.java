package com.app.hos.security.states;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface AuthenticationState {
	
	public void doAuthentication(StatesAuthenticator authentication, ServletRequest request, 
			ServletResponse response, FilterChain chain) throws IOException, ServletException;
	
}
