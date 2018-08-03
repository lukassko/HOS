package com.app.hos.service.command.builder_v2.concretebuilders;

import com.app.hos.service.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.service.command.builder_v2.CommandDescriptor;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.command.type.DeviceType;

@CommandDescriptor(
	device={DeviceType.SERVER, DeviceType.PHONE, DeviceType.TV}, 
	type=CommandType.GET_STATUS
)
public class GetStatusCommandBuilder extends AbstractCommandBuilder {

	@Override
	public AbstractCommandBuilder setCommandType() {
		command.setCommandType(CommandType.GET_STATUS);
		return this;
	}

	@Override
	public AbstractCommandBuilder setResult() {
		return this;
	}
	
	@Override
	public AbstractCommandBuilder setStatus() {
		command.setStatus(true);
		return this;
	}

}
