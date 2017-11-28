package com.app.hos.service.websocket.command.builder.concretebuilders;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;

public class RemoveDeviceWebCommandBuilder extends AbstractWebCommandBuilder {

	@Autowired
	protected DeviceManager deviceManager;
	
	@Override
	public void setCommandType() {
		command.setType(WebCommandType.REMOVE_DEVICE);
	}

}
