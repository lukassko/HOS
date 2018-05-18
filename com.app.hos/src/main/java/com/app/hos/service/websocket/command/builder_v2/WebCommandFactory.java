package com.app.hos.service.websocket.command.builder_v2;

import com.app.hos.service.websocket.command.WebCommandType;


public class WebCommandFactory { 

	private static WebCommandBuilder webCommandBuilder = new WebCommandBuilder();
	
	
	public static WebCommand get(WebCommandType commantType) {
		return WebCommandFactory.get(commantType, null);
	}
	
	public static WebCommand get(WebCommandType commantType, String message) {
		AbstractWebCommandBuilder builder = commantType.create();
		webCommandBuilder.setCommandBuilder(builder);
		return webCommandBuilder.createCommand();
	}
}
