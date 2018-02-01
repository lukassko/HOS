package com.app.hos.service.websocket.command.builder.concretebuilders;

import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

public class GetDeviceStatusesWebCommandBuilder extends AbstractWebCommandBuilder {

	@Override
	public void setCommandType() {
		command.setType(WebCommandType.GET_DEVICE_STATUSES);
	}

	@Override
	public void setStatus() {
		command.setStatus(true);
	}
}
