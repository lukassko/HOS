package com.app.hos.share.command.builder;

import java.util.concurrent.Callable;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.decorators.GetAllDeviceWebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.share.command.decorators.GetStatusCommand;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.exceptions.NotExecutableCommandException;

@Service
public class CommandConverter implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	public static Callable<Command> getExecutableCommand(Command command) throws NotExecutableCommandException {
		CommandType type = CommandType.valueOf(command.getCommandType());
		Callable<Command> executableCommand = null; 
		if (type == CommandType.GET_STATUS) {
			executableCommand = new GetStatusCommand(command);
		} else {
			throw new NotExecutableCommandException(type);
		}
		return executableCommand;
	}
	
	public static Callable<WebCommand> getExecutableWebCommand(WebCommand command) throws NotExecutableCommandException {
		WebCommandType type = command.getType();
		Callable<WebCommand> executableCommand = null; 
		if (type == WebCommandType.GET_ALL_DEVICES) {
			executableCommand = (GetAllDeviceWebCommand)applicationContext.getBean("allDeviceBuilder",command);
		} else if (type == WebCommandType.REMOVE_DEVICE) {
		} else {
			//throw new NotExecutableCommand(type);
		}
		return executableCommand;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CommandConverter.applicationContext = applicationContext;
	}
}
