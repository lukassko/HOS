package com.app.hos.config.init;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.command.CommandInfo;
import com.app.hos.service.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.service.command.builder_v2.Command;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerInfo;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;

@Profile("!web-integration-test")
@Component
public class PostContextLoader implements ApplicationListener<ContextRefreshedEvent>, Ordered {
	
	@Autowired
	private AbstractMapFactory<Class<? extends Throwable>, 
								HOSExceptionHandler<? extends Throwable>,
								HOSExceptionHandlerInfo> exceptionHandlerFactory;

	@Autowired
	private AbstractMapFactory<CommandType, Class<? extends AbstractCommandBuilder>, Command> commandFactory;
	
	@Autowired
	//@Qualifier("futureWebCommandFactory")
	private AbstractMapFactory<Object,String, Callable<WebCommand>> futureWebCommandFactory;
	
	@Autowired
	//@Qualifier("futureCommandFactory")
	private AbstractMapFactory<Object,String, Callable<CommandInfo>> futureCommandFactory;
	
	@Override
	public int getOrder() {
		return 0;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		exceptionHandlerFactory.register("com.app.hos.utils.exceptions.handler");
		commandFactory.register("com.app.hos.share.command.builder_v2.concretebuilders");
		futureWebCommandFactory.register("com.app.hos.service.websocket.command.decorators");
		futureCommandFactory.register("com.app.hos.share.command.decorators");
	}

}
