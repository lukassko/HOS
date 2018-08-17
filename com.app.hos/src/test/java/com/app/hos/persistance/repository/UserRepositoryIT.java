package com.app.hos.persistance.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.models.user.Role;
import com.app.hos.persistance.models.user.User;
import com.app.hos.persistance.models.user.Role.UserRole;
import com.app.hos.persistance.repository.UserRepository;
import com.app.hos.utils.security.SecurityUtils;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class UserRepositoryIT {

	@Autowired
    private UserRepository userRepository;
	
	//@Rollback(false)
    @Test
    public void stage10_saveOneUserAndCheckIfWasSavedTest() {
		// given
    	User user = new User();
    	user.setName("admin");
    	user.setHash(SecurityUtils.getRandomAsString());
		user.setSalt(SecurityUtils.getRandomAsString());
    	
    	Role role1 = new Role(UserRole.USER);
    	Role role2 = new Role(UserRole.ADMIN);

    	user.addRole(role1);
    	user.addRole(role2);
    	
    	Assert.assertTrue(role1.getUsers().size() == 1);
    	Assert.assertTrue(role2.getUsers().size() == 1);
    	Assert.assertTrue(user.getRoles().size() == 2);
    	
    	// when
    	userRepository.save(user);
    	
    	// then
    	Assert.assertEquals(user, userRepository.find(1));
    	Assert.assertEquals(user, userRepository.findByName("admin"));
    	Assert.assertEquals(2, userRepository.findByName("admin").getRoles().size());
    }
 
	@Test
	public void stage20_selectMultiUsersShouldReturnProperUsersList() throws Exception {
		// given
		User user = new User();
    	user.setName("user_1");
    	user.setHash(SecurityUtils.getRandomAsString());
		user.setSalt(SecurityUtils.getRandomAsString());
    	Role role = new Role(UserRole.USER);
    	user.addRole(role);
    	
    	userRepository.save(user);
    	
    	user = new User();
    	user.setName("admin_1");
    	user.setHash(SecurityUtils.getRandomAsString());
		user.setSalt(SecurityUtils.getRandomAsString());
    	role = new Role(UserRole.ADMIN);
    	user.addRole(role);
    	
    	userRepository.save(user);
    	
    	user = new User();
    	user.setName("admin_2");
    	user.setHash(SecurityUtils.getRandomAsString());
		user.setSalt(SecurityUtils.getRandomAsString());
    	role = new Role(UserRole.ADMIN);
    	user.addRole(role);
    	
    	userRepository.save(user);
    	
		// when
    	List<User> allUsers = userRepository.findAll().getAsLIst();
    	List<User> admins = userRepository.findAll().hasRole(UserRole.ADMIN).getAsLIst();
    	
		// then
    	assertEquals(2, admins.size());
    	assertEquals(3, allUsers.size());
	}
}
