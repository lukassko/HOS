package com.app.hos.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.app.hos.pojo.UserChallenge;
import com.app.hos.utils.security.SecurityUtils;

@Service
public class HosAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserChallenge userHash = (UserChallenge)authentication.getCredentials();

		if (isChallengeCorrect(userHash)) {
			authentication.setAuthenticated(true);
		} else {
			throw new BadCredentialsException("Invalid user password");
		}
		
		return authentication;
	}

	private boolean isChallengeCorrect(UserChallenge userChallenge) {
		// calculate in browser hash((hash(pass + salt) + challenge))
		String userOneTimeRequest = userChallenge.getOneTimeRequest(); 
		String challenge = userChallenge.getChallenge();
		String userHash = userChallenge.getHash();
		String hashedOneTimeRequest = SecurityUtils.hash(userHash + challenge);
		return hashedOneTimeRequest.equals(userOneTimeRequest);
	}
	
	@Override
	public boolean supports(Class<?> authenticationClazz) {
		return false;
	}

}
