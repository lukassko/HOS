package com.app.hos.config.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerFactory;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;

@Profile("!web-integration-test")
@Component
public class PostContextLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private HOSExceptionHandlerFactory exceptionHandlerFactory;
	
	@Autowired
	private FutureWebCommandFactory futureWebCommandFactory;
	
	@Autowired
	@Qualifier("commandFactory")
	private AbstractMapFactory commandFactory;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		exceptionHandlerFactory.registerHandlers("com.app.hos.utils.exceptions.handler");
		futureWebCommandFactory.registerFactories("com.app.hos.service.websocket.command.future.concretefactories");
		commandFactory.register("com.app.hos.share.command.builder_v2.concretebuilders");
	}

}
