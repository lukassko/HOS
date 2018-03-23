package com.app.hos.tests.units.models;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.Role;
import com.app.hos.persistance.models.Role.UserRole;

public class RoleTest {

	@Test
	public void testHashCodeForThisSameRoles() {
		// given
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.USER);
		int role1hash = role1.hashCode();
		int role2hash = role2.hashCode();
		
		//when then
		Assert.assertTrue(role1hash==role2hash);
	}
	
	@Test
	public void testHashCodeForDiffrentRoles() {
		// given
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.ADMIN);
		int role1hash = role1.hashCode();
		int role2hash = role2.hashCode();
		
		//when then
		Assert.assertFalse(role1hash==role2hash);
	}
	
	@Test
	public void testEqualForThisSameRoles() {
		// given
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.USER);
		
		//when then
		Assert.assertTrue(role1.equals(role2));
	}
	
	@Test
	public void testEqualsForDiffrentRoles() {
		// given
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.ADMIN);
		
		//when then
		Assert.assertFalse(role1.equals(role2));
	}
	
}
