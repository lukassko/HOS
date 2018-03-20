package com.app.hos.security.detailservice;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsWithHashing extends UserDetails {

	public String getHash();
	
	public String getSalt();
	
}
