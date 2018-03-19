package com.app.hos.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserHashing extends UserDetails {

	public String getHash();
	
	public String getSalt();
	
}
