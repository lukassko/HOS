package com.app.hos.service.websocket.command;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.builder.WebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;


public class WebCommandFactory {
	
	private static WebCommandBuilder commandBuilder = new WebCommandBuilder();
	
	public static WebCommand getCommand(WebCommandType type) {
		if(type == null) {
			throw new IllegalArgumentException();
		}
		if (type == WebCommandType.REMOVE_DEVICE) {
			commandBuilder.setCommandBuilder(new RemoveDeviceWebCommandBuilder());
		} else {
			return null;
		}
		
 		return commandBuilder.getCommand();
	}
}
