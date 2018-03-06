package com.app.hos.tests.integrations.persistance;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.models.User;
import com.app.hos.persistance.models.User.Role;
import com.app.hos.persistance.repository.UserRepository;

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
	
	 
    @Test
    public void stage1_saveOneUserAndCheckIfWasSavedTest() {
    	User user = new User();
    	user.setName("Lukasz");
    	user.setPassword("moje_haslo");
    	user.setRole(Role.ADMIN);
    	
    	userRepository.save(user);
    	
    	Assert.assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
    	Assert.assertEquals(user, userRepository.find(1));
    	Assert.assertEquals(user, userRepository.find("Lukasz", "moje_haslo"));
    }
}
