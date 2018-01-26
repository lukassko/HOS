package com.app.hos.service.websocket.command.builder.concretebuilders;

import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

public class BadConversionWebCommandBuilder extends AbstractWebCommandBuilder {

	public BadConversionWebCommandBuilder(String message) {
		super(message);
	}
	
	@Override
	public void setCommandType() {
		command.setType(WebCommandType.JSON_EXCEPTION);
	}

	@Override
	public void setStatus() {
		command.setStatus(false);
	}
}
