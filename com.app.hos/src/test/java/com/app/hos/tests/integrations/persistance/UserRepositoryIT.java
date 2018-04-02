package com.app.hos.tests.integrations.persistance;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.models.Role;
import com.app.hos.persistance.models.Role.UserRole;
import com.app.hos.persistance.models.User;
import com.app.hos.persistance.repository.UserRepository;
import com.app.hos.utils.security.SecurityUtils;

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
	
	@Rollback(false)
    @Test
    public void stage10_saveOneUserAndCheckIfWasSavedTest() {
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
    	
    	userRepository.save(user);
    	
    	Assert.assertEquals(user, userRepository.find(1));
    	Assert.assertEquals(user, userRepository.findByName("admin"));
    	Assert.assertEquals(2, userRepository.findByName("admin").getRoles().size());
    	Assert.assertTrue(user.compareRoles(userRepository.findByName("admin")));
    }
    
}
