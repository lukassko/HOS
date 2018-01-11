package com.app.hos.config.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.app.hos.utils.exceptions.handler.HOSExceptionHandlerFactory;

@Component
public class PostContextLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private HOSExceptionHandlerFactory exceptionHandlerFactory;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		exceptionHandlerFactory.registerHandlers("com.app.hos.utils.exceptions.handler");
	}

}
