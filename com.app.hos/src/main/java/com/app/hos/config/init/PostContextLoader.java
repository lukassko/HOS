package com.app.hos.config.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerInfo;
import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.type.CommandType;

@Profile("!web-integration-test")
@Component
public class PostContextLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private AbstractMapFactory<Class<? extends Throwable>, 
								HOSExceptionHandler<? extends Throwable>,
								HOSExceptionHandlerInfo> exceptionHandlerFactory;

	@Autowired
	private AbstractMapFactory<CommandType, Class<? extends AbstractCommandBuilder>, Command> commandFactory;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		exceptionHandlerFactory.register("com.app.hos.utils.exceptions.handler");
		commandFactory.register("com.app.hos.share.command.builder_v2.concretebuilders");
	}

}
