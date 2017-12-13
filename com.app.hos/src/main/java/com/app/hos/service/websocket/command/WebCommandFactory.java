package com.app.hos.service.websocket.command;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.builder.WebCommandBuilder;
import com.app.hos.service.websocket.command.builder.concretebuilders.BadConversionWebCommandBuilder;
import com.app.hos.service.websocket.command.builder.concretebuilders.GetAllDevicesWebCommandBuilder;
import com.app.hos.service.websocket.command.builder.concretebuilders.RemoveDeviceWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

public class WebCommandFactory {
    
	private static WebCommandBuilder commandBuilder = new WebCommandBuilder();
	
	public static WebCommand getCommand(WebCommandType type) {
		return getCommand(type, null);
	}

	public static WebCommand getCommand(WebCommandType type, String message) {
		if(type == null) {
			throw new IllegalArgumentException();
		}
		if (type == WebCommandType.REMOVE_DEVICE) {
			commandBuilder.setCommandBuilder(new RemoveDeviceWebCommandBuilder());
		} else if (type == WebCommandType.GET_ALL_DEVICES) {
			commandBuilder.setCommandBuilder(new GetAllDevicesWebCommandBuilder());
		} else if (type == WebCommandType.BAD_COMMAND_CONVERSION) {
			commandBuilder.setCommandBuilder(new BadConversionWebCommandBuilder(message));
		} else {
			return null;
		}	
		commandBuilder.createCommand();
 		return commandBuilder.getCommand();
	}
	
}
