package com.app.hos.service.websocket.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.builder.WebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

@Component
public class WebCommandFactory {
	
	private WebCommandBuilder commandBuilder = new WebCommandBuilder();
	
	@Autowired
	private GetAllDevicesWebCommandBuilder getAllDevicesWebCommandBuilder;

	public WebCommand getCommand(WebCommandType type) {
		if(type == null) {
			throw new IllegalArgumentException();
		}
		if (type == WebCommandType.REMOVE_DEVICE) {
			commandBuilder.setCommandBuilder(new RemoveDeviceWebCommandBuilder());
		} else if (type == WebCommandType.GET_ALL_DEVICES) {
			commandBuilder.setCommandBuilder(getAllDevicesWebCommandBuilder);
		} else {
			return null;
		}
		commandBuilder.createCommand();
 		return commandBuilder.getCommand();
	}
}
