package com.app.hos.service.websocket.command.builder.concretebuilders;

import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

public class GetAllDevicesWebCommandBuilder extends AbstractWebCommandBuilder {

	@Override
	public void setCommandType() {
		command.setType(WebCommandType.GET_ALL_DEVICES);
	}

	@Override
	public void setStatus() {
		command.setStatus(true);
	}
}
