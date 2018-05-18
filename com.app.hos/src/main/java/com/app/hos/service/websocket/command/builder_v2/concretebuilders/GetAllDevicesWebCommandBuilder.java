package com.app.hos.service.websocket.command.builder_v2.concretebuilders;

import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.AbstractWebCommandBuilder;

public class GetAllDevicesWebCommandBuilder extends AbstractWebCommandBuilder {

	@Override
	public AbstractWebCommandBuilder setCommandType() {
		command.setType(WebCommandType.GET_ALL_DEVICES);
		return this;
	}

	@Override
	public AbstractWebCommandBuilder setStatus() {
		command.setStatus(true);
		return this;
	}
}
