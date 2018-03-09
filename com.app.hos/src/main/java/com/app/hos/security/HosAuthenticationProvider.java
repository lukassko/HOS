package com.app.hos.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class HosAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserDetails userDetail = (UserDetails)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		String userPassword = userDetail.getPassword();
		
		// just for testing compare string
		// later protect password with hash and salt
		if (userPassword.equals(password)) {
			authentication.setAuthenticated(true);
		} else {
			throw new BadCredentialsException("Invalid user password");
		}
		
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authenticationClazz) {
		return false;
	}
}
