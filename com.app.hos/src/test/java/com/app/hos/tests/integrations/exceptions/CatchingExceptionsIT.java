package com.app.hos.tests.integrations.exceptions;

import org.junit.FixMethodOrder;
import org.junit.Ignore;

import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.tests.utils.Thrower;


@Ignore("run only one integration test")
@WebAppConfiguration 
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class CatchingExceptionsIT {

	// test such scenario:
	// catch IOException (throw JsonException) and check if exact exception was handled (JsonException)
	
	Thrower thrower = new Thrower();
	
}
