package com.app.hos.service.websocket.command.builder_v2.concretebuilders;

import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.AbstractWebCommandBuilder;

public class JsonExceptionWebCommandBuilder extends AbstractWebCommandBuilder {
	
	public JsonExceptionWebCommandBuilder(String message) {
		super(message);
	}
	
	@Override
	public AbstractWebCommandBuilder setCommandType() {
		command.setType(WebCommandType.JSON_EXCEPTION);
		return this;
	}

	@Override
	public AbstractWebCommandBuilder setStatus() {
		command.setStatus(false);
		return this;
	}
}
