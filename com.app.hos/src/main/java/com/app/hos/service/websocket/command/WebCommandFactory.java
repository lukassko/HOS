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
			System.out.println("OK1");
			commandBuilder.setCommandBuilder(new RemoveDeviceWebCommandBuilder());
		} else if (type == WebCommandType.GET_ALL_DEVICES) {
			System.out.println("OK2");
			commandBuilder.setCommandBuilder(new GetAllDevicesWebCommandBuilder());
		} else {
			System.out.println("OK3");
			return null;
		}
		commandBuilder.createCommand();
 		return commandBuilder.getCommand();
	}
}
