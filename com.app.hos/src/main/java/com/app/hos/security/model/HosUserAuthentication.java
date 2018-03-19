package com.app.hos.security.model;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.app.hos.security.UserHashing;

@SuppressWarnings("serial")
public class HosUserAuthentication implements Authentication {

	private UserHashing userDetails;
	private Object credentials;
	private boolean isAuthenticated;
	
	public HosUserAuthentication(UserHashing userDetails) {
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
		return this.isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

}
