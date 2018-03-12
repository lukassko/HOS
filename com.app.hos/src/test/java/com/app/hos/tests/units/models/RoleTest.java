package com.app.hos.tests.units.models;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.Role;
import com.app.hos.persistance.models.Role.UserRole;

public class RoleTest {

	@Test
	public void testHashCodeForThisSameRoles() {
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.USER);
		int role1hash = role1.hashCode();
		int role2hash = role2.hashCode();
		Assert.assertTrue(role1hash==role2hash);
	}
	
	@Test
	public void testHashCodeForDiffrentRoles() {
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.ADMIN);
		int role1hash = role1.hashCode();
		int role2hash = role2.hashCode();
		Assert.assertFalse(role1hash==role2hash);
	}
	
	@Test
	public void testEqualForThisSameRoles() {
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.USER);
		Assert.assertTrue(role1.equals(role2));
	}
	
	@Test
	public void testEqualsForDiffrentRoles() {
		Role role1 = new Role(UserRole.USER);
		Role role2 = new Role(UserRole.ADMIN);
		Assert.assertFalse(role1.equals(role2));
	}
	
}
