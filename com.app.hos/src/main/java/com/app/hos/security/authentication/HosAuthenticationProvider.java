package com.app.hos.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.utils.security.SecurityUtils;

@Service
public class HosAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserDetails userDetail = (UserDetails)authentication.getPrincipal();
		UserChallenge userHash = (UserChallenge)authentication.getCredentials();

		if (isChallengeCorrect(userHash,userDetail)) {
			authentication.setAuthenticated(true);
		} else {
			throw new BadCredentialsException("Invalid user password");
		}
		
		return authentication;
	}

	private boolean isChallengeCorrect(UserChallenge userHash, UserDetails userHashing) {
		
		String oneTimeHash = userHash.getHash();
		
		String challenge = userHash.getChallenge();
		String userStoredHash = userHashing.getHash();
		String hashedOneTimeRequest = SecurityUtils.hashString(challenge + userStoredHash);
		
		return hashedOneTimeRequest.equals(oneTimeHash);
		
	}
	
	@Override
	public boolean supports(Class<?> authenticationClazz) {
		return false;
	}

}
