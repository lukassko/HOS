package com.app.hos.security.states;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;

import com.app.hos.security.states.concretestates.UnauthenticatedState;

public class StatesAuthenticator {

	private AuthenticationState state;
	
	private Optional<Authentication> authentication = Optional.empty();
	
	public StatesAuthenticator () {
		this.state = new UnauthenticatedState();
	}
	
	public void doAuthentication(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		state.doAuthentication(this,request,response,chain);
	}

	public AuthenticationState getState() {
		return state;
	}

	public void setState(AuthenticationState state) {
		this.state = state;
	}
	
	public Optional<Authentication> getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = Optional.ofNullable(authentication);
	}
}
