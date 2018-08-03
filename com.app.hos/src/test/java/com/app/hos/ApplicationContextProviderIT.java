package com.app.hos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.utils.ReflectionUtils;


@Ignore("run only one integration test")
@WebAppConfiguration 
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationContextProviderIT {

	
	// Test also Utils method for scanClasses
	
	@Test
	public void stage10_checkIfGetBeanWillReturnNonNullObject() {
		Assert.assertNotNull(ReflectionUtils.getObjectFromContext("hosServer"));
		Assert.assertNotNull(ReflectionUtils.getObjectFromContext(DeviceManager.class));
		Assert.assertNotNull(ReflectionUtils.getObjectFromContext(DeviceRepository.class));
	}	
	
	@Test(expected = NoSuchBeanDefinitionException.class)
	public void stage20_checkIfGetBeanWillThrowExcpetion() {
		ReflectionUtils.getObjectFromContext("thereIsNoSuchBean");
	}	

}