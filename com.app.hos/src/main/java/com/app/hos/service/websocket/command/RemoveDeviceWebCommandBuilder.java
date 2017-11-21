package com.app.hos.service.websocket.command;

import org.springframework.stereotype.Component;

import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

//@Component
public class RemoveDeviceWebCommandBuilder extends AbstractWebCommandBuilder {

	@Override
	public void setCommandType() {
		command.setType(WebCommandType.REMOVE_DEVICE);
	}

	@Override
	public void setMessage() {
	}

}
