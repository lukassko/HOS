package com.app.hos.service.command.builder_v2.concretebuilders;

import com.app.hos.service.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.service.command.builder_v2.CommandDescriptor;
import com.app.hos.service.command.result.Message;
import com.app.hos.service.command.result.Result;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.command.type.DeviceType;

@CommandDescriptor(
		device={DeviceType.SERVER, DeviceType.PHONE, DeviceType.TV}, 
		type=CommandType.BAD_CONVERSION
)
public class BadConversionCommandBuilder extends AbstractCommandBuilder {

	private String message = null;
	
	public BadConversionCommandBuilder(String message) {
		this.message = message;
	}
	
	@Override
	public AbstractCommandBuilder setCommandType() {
		command.setCommandType(CommandType.BAD_CONVERSION);
		return this;
	}

	@Override
	public AbstractCommandBuilder setResult() {
		Result result = new Message(this.message);
		command.setResult(result);
		return this;
	}

	@Override
	public AbstractCommandBuilder setStatus() {
		command.setStatus(false);
		return this;
	}

}
