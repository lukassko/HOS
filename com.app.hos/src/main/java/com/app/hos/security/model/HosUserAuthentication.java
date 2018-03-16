package com.app.hos.security.model;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class HosUserAuthentication implements Authentication {

	private UserDetails userDetails;
	private Object credentials;
	
	public HosUserAuthentication(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	@Override
	public String getName() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userDetails.getAuthorities();
	}

	public HosUserAuthentication setCredentials(Object credentials) {
		this.credentials = credentials;
		return this;
	}
	
	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
	}

}
