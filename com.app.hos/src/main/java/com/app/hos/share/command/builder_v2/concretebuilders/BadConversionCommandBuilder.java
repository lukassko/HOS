package com.app.hos.share.command.builder_v2.concretebuilders;

import com.app.hos.share.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.share.command.builder_v2.CommandDescriptor;
import com.app.hos.share.command.result.Message;
import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;

@CommandDescriptor(
		device={DeviceType.SERVER, DeviceType.PHONE, DeviceType.TV}, 
		type=CommandType.GET_STATUS
)
public class BadConversionCommandBuilder extends AbstractCommandBuilder {

	private String message = null;
	
	public BadConversionCommandBuilder(String message) {
		this.message = message;
	}
	
	@Override
	public AbstractCommandBuilder setCommandType() {
		String type = CommandType.BAD_COMMAND_CONVERSION.toString();
		command.setCommandType(type);
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
