package com.app.hos.tests.integrations.service.exceptions;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.service.exceptions.handler.ExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerFactory;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerInfo;
import com.app.hos.service.exceptions.handler.instance.WebSocketJsonExceptionHandler;
import com.app.hos.utils.ReflectionUtils;
import com.fasterxml.jackson.core.JsonParseException;

@Ignore("run only one integration test")
@WebAppConfiguration 
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionRegisteringIT {
	
	@Autowired
	private HOSExceptionHandlerFactory exceptionHandlerFactory;
		 
	@Test
	public void stage10_checkIfHandlersExists() {
		List<String> handlers = findHandlers();
		Assert.assertFalse(handlers.isEmpty());
	}
	
	@Test
	public void stage20_checkIfhandlersClassExists() {
		List<String> handlers = findHandlers();
		for(String handler : handlers) {
			Assert.assertNotNull(ReflectionUtils.getClass(handler));
		}
	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void stage30_checkIfhandlersClassExistsInSpringContext() {
		List<String> handlers = findHandlers();
		for(String handler : handlers) {
			Class<?> hanlderClazz = ReflectionUtils.getClass(handler);
			Object bean = ReflectionUtils.getObjectFromContext(hanlderClazz);
			HOSExceptionHandler excpetionHandler = (HOSExceptionHandler)bean;
			Class exceptoinClazz = ReflectionUtils.getGenericParamter(handler);
			Assert.assertNotNull(exceptoinClazz);
			Assert.assertNotNull(excpetionHandler);
		}
	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void stage40_checkIfhandlersForJsonParseExistsInSpringContext() {
		List<String> handlers = findHandlers();
		for(String handler : handlers) {
			Class<?> hanlderClazz = ReflectionUtils.getClass(handler);
			if (hanlderClazz.equals(WebSocketJsonExceptionHandler.class)) {
				Class exceptoinClazz = ReflectionUtils.getGenericParamter(handler);
				Assert.assertTrue(exceptoinClazz.equals(JsonParseException.class));
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test
	public void stage50_checkIfhandlersExistsInFacorty() {
		HOSExceptionHandlerInfo handlerInfo = exceptionHandlerFactory.getHandler(JsonParseException.class);
		Assert.assertNotNull(handlerInfo);
		Assert.assertNotNull(exceptionHandlerFactory.getHandler(JsonParseException.class));
	}
	
	private List<String> findHandlers() {
	    String[] packages = {"com.app.hos.utils.exceptions.handler.instance"};
		return ReflectionUtils.scanForAnnotation(ExceptionHandler.class,packages);
	}
}
