package com.app.hos.service.websocket.command;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.builder.WebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

@Component
public class WebCommandFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    
	private WebCommandBuilder commandBuilder = new WebCommandBuilder();
	

	public WebCommand getCommand(WebCommandType type) {
		if(type == null) {
			throw new IllegalArgumentException();
		}
		if (type == WebCommandType.REMOVE_DEVICE) {
//			commandBuilder.setCommandBuilder(removeDeviceWebCommandBuilder);
		} else if (type == WebCommandType.GET_ALL_DEVICES) {
			commandBuilder.setCommandBuilder((GetAllDevicesWebCommandBuilder)applicationContext.getBean("allDeviceBuilder"));
		} else {
			return null;
		}	
		commandBuilder.createCommand();
 		return commandBuilder.getCommand();
	}


	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
