package com.app.hos.tests.integrations.persistance;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.AspectConfig;

import com.app.hos.persistance.repository.ConnectionRepository;


import com.app.hos.tests.integrations.config.PersistanceConfig;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class , AspectConfig.class})
@ActiveProfiles("test-sqlite")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectionRepositoryIT {

	
	@Autowired
    private ConnectionRepository connectionRepository;
	
	public void test () {
		Assert.assertEquals(1,1);
	}
   
}
