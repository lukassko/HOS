package com.app.hos.tests.units.models;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.Role;
import com.app.hos.persistance.models.Role.UserRole;
import com.app.hos.persistance.models.User;

public class UserTest {

	@Test
	public void testEqueslOfUserRolesShouldReturnTrue() {
		User user1 = new User("Lukasz","password");
		User user2 = new User("Michal","password");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		users1Roles.add(new Role(UserRole.ADMIN));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		Assert.assertTrue(user1.compareRoles(user2));
	}
	
	@Test
	public void testEqueslOfUserRolesShouldReturnTrueByAddingDoubleRole() {
		User user1 = new User("Lukasz","password");
		User user2 = new User("Michal","password");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		users1Roles.add(new Role(UserRole.ADMIN));
		users1Roles.add(new Role(UserRole.ADMIN)); // HashSet should not allow to double user role
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		Assert.assertTrue(user1.compareRoles(user2));
	}
	
	@Test
	public void testEqueslOfUserRolesShouldReturnFalse() {
		User user1 = new User("Lukasz","password");
		User user2 = new User("Michal","password");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		Assert.assertFalse(user1.compareRoles(user2));
	}
	
	@Test
	public void compareTwoUsersWithoueRolesShouldReturnTrue() {
		User user1 = new User("Lukasz","password");
		User user2 = new User("Lukasz","password");
		Assert.assertTrue(user1.equals(user2));
	}
	
	@Test
	public void compareTwoUsersWithRolesShouldReturnTrue() {
		User user1 = new User("Lukasz","password");
		User user2 = new User("Lukasz","password");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		users1Roles.add(new Role(UserRole.ADMIN));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		Assert.assertTrue(user1.equals(user2));
	}
	
	@Test
	public void compareTwoUsersWithoutRolesShouldReturnFalse() {
		User user1 = new User("Lukasz","password");
		User user2 = new User("Michal","password");
		Assert.assertFalse(user1.equals(user2));
	}

	@Test
	public void compareTwoUsersWithRolesShouldReturnFalse() {
		User user1 = new User("Lukasz","password");
		User user2 = new User("Michal","password");
		
		Set<Role> users1Roles = new HashSet<>();
		users1Roles.add(new Role(UserRole.USER));
		user1.setRoles(users1Roles);
		
		Set<Role> users2Roles = new HashSet<>();
		users2Roles.add(new Role(UserRole.ADMIN));
		users2Roles.add(new Role(UserRole.USER));
		user2.setRoles(users2Roles);
		
		Assert.assertFalse(user1.equals(user2));
	}
}
