package com.app.hos.security.authenticating;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;

import com.app.hos.persistance.models.user.User;
import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.authentication.HosAuthenticationProvider;
import com.app.hos.security.authentication.HosUserAuthentication;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.utils.security.SecurityUtils;

public class HosAuthenticationProviderTest {

	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private HosAuthenticationProvider authenticationProvider = new HosAuthenticationProvider();

	@Test
	public void authenticationProviderShouldCorrectAuthenticateUserWhenHashAndSaltAreCorrect() {
		// given
		User user = new User("Lukasz");
		String password = "root";
		
		String userSalt = SecurityUtils.getRandomAsString();
		String saltedPassword = SecurityUtils.hash(password + userSalt);
		
		user.setHash(saltedPassword);
		user.setSalt(userSalt);
		
		String challenge = SecurityUtils.getRandomAsString();
		
		// simulate hashing in browser client
		String hashedOneTimeRequest = SecurityUtils.hash(password + userSalt);
		hashedOneTimeRequest = SecurityUtils.hash(hashedOneTimeRequest + challenge);
		UserChallenge userChallenge = new UserChallenge().setHash(hashedOneTimeRequest).setChallenge(challenge);
		
		UserDetails userDetails = new HosUserDetails(user);
		HosUserAuthentication authentication = new HosUserAuthentication(userDetails);

		// when
		authenticationProvider.authenticate(authentication.setCredentials(userChallenge));
		
		// then
		Assert.assertTrue(authentication.isAuthenticated());
	}
	
	@Test(expected=BadCredentialsException.class)
	public void authenticationProviderShouldThrowExceptionAfterIncorrectAuthenticateUser() {
		// given
		User user = new User("Lukasz");
		String password = "root";
		
		String userSalt = SecurityUtils.getRandomAsString();
		String saltedPassword = SecurityUtils.hash(password + userSalt);
			
		user.setHash(saltedPassword);
		user.setSalt(userSalt);
			
		String challenge = SecurityUtils.getRandomAsString();
				
		// simulate hashing in browser client
		String hashedOneTimeRequest = SecurityUtils.hash("bad_password" + userSalt);
		hashedOneTimeRequest = SecurityUtils.hash(hashedOneTimeRequest + challenge);
		UserChallenge userChallenge = new UserChallenge().setHash(hashedOneTimeRequest).setChallenge(challenge);
				
		UserDetails userDetails = new HosUserDetails(user);
		HosUserAuthentication authentication = new HosUserAuthentication(userDetails);

		// when
		authenticationProvider.authenticate(authentication.setCredentials(userChallenge));
		
		// then throw exception
	}
	
}
