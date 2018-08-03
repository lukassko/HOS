package com.app.hos.persistance.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.user.Role;
import com.app.hos.persistance.models.user.User;
import com.app.hos.persistance.models.user.Role.UserRole;

public class UserTest {

	@Test
	public void testEqueslOfUserRolesShouldReturnTrue() {
		// given
		User user1 = new User("Lukasz");
		User user2 = new User("Michal");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		users1Roles.add(new Role(UserRole.ADMIN));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		// when then
		Assert.assertTrue(user1.compareRoles(user2));
	}
	
	@Test
	public void testEqueslOfUserRolesShouldReturnTrueByAddingDoubleRole() {
		// given
		User user1 = new User("Lukasz");
		User user2 = new User("Michal");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		users1Roles.add(new Role(UserRole.ADMIN));
		users1Roles.add(new Role(UserRole.ADMIN)); // HashSet should not allow to double user role
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		// when then
		Assert.assertTrue(user1.compareRoles(user2));
	}
	
	@Test
	public void testEqueslOfUserRolesShouldReturnFalse() {
		// given
		User user1 = new User("Lukasz");
		User user2 = new User("Michal");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		// when then
		Assert.assertFalse(user1.compareRoles(user2));
	}
	
	@Test
	public void compareTwoUsersWithoueRolesShouldReturnTrue() {
		// given
		User user1 = new User("Lukasz");
		User user2 = new User("Lukasz");
		
		// when then
		Assert.assertTrue(user1.equals(user2));
	}
	
	@Test
	public void compareTwoUsersWithRolesShouldReturnTrue() {
		// given
		User user1 = new User("Lukasz");
		User user2 = new User("Lukasz");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		users1Roles.add(new Role(UserRole.ADMIN));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		// when then
		Assert.assertTrue(user1.equals(user2));
	}
	
	@Test
	public void compareTwoUsersWithoutRolesShouldReturnFalse() {
		User user1 = new User("Lukasz");
		User user2 = new User("Michal");
		
		// when then
		Assert.assertFalse(user1.equals(user2));
	}

	@Test
	public void compareTwoUsersWithRolesShouldReturnFalse() {
		// given
		User user1 = new User("Lukasz");
		User user2 = new User("Michal");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		// when then
		Assert.assertFalse(user1.equals(user2));
	}
}
