package com.app.hos.security.detailservice;

public interface UserDetails extends org.springframework.security.core.userdetails.UserDetails {

	public String getHash();
	
	public String getSalt();
	
}
