package com.app.hos.service.websocket.command;

import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

public class RemoveDeviceWebCommandBuilder extends AbstractWebCommandBuilder {

	@Override
	public void setCommandType() {
		command.setType(WebCommandType.REMOVE_DEVICE.toString());
	}

	@Override
	public void setMessage() {
		// TODO Auto-generated method stub
		
	}

}
