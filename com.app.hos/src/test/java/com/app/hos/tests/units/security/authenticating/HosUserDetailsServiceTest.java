package com.app.hos.tests.units.security.authenticating;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.hos.persistance.models.Role;
import com.app.hos.persistance.models.Role.UserRole;
import com.app.hos.persistance.models.User;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.detailservice.HosUserDetailsService;
import com.app.hos.service.managers.UserManager;
import com.app.hos.utils.security.SecurityUtils;

import org.junit.Assert;

public class HosUserDetailsServiceTest {

	@Mock
	private UserManager userManager;

	private UserDetailsService userDetailsService;
	
	@Before
	public void setUp() throws ServletException {
		MockitoAnnotations.initMocks(this);
		userDetailsService = new HosUserDetailsService(userManager);
	}
	
	@Test
	public void userDetailsServiceShouldReturnCorrectUserDetailsWithoutRolesAfterSpecificUserWasFound() {
		// given
		User user = new User("Lukasz");
		String userHash = SecurityUtils.getRandomAsString();
		String userSalt = SecurityUtils.getRandomAsString();
		user.setHash(userHash);
		user.setSalt(userSalt);
		UserDetails userDetails = new HosUserDetails(user);
		
		Mockito.when(userManager.findUserByName("Lukasz")).thenReturn(user);
		
		// when
		UserDetails foundedUserDetails = userDetailsService.loadUserByUsername("Lukasz");
		
		// then
		Assert.assertEquals(userDetails, foundedUserDetails);
	}
	
	@Test
	public void userDetailsServiceShouldReturnCorrectUserDetailsWithRolesAfterSpecificUserWasFound() {
		// given
		User user = new User("Lukasz");
		String userHash = SecurityUtils.getRandomAsString();
		String userSalt = SecurityUtils.getRandomAsString();
		user.setHash(userHash);
		user.setSalt(userSalt);
		user.addRole(new Role(UserRole.ADMIN));
		user.addRole(new Role(UserRole.USER));
		UserDetails userDetails = new HosUserDetails(user);
		
		Mockito.when(userManager.findUserByName("Lukasz")).thenReturn(user);
		
		// when
		UserDetails foundedUserDetails = userDetailsService.loadUserByUsername("Lukasz");
		
		// then
		Assert.assertEquals(userDetails, foundedUserDetails);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void userDetailsServiceShouldReturnShouldThrowExceptionWhenSpecificUserWasNotFound() {
		// given
		Mockito.when(userManager.findUserByName("Lukasz")).thenThrow(new UsernameNotFoundException("Michal"));
		
		// when
		userDetailsService.loadUserByUsername("Lukasz");
		
		// then throw exception
	}
}
