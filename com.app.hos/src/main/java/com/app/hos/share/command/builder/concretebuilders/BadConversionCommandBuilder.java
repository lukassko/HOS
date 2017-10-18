package com.app.hos.share.command.builder.concretebuilders;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.type.CommandType;

public class BadConversionCommandBuilder extends AbstractCommandBuilder {

	@Override
	public void setCommandType() {
		String type = CommandType.BAD_COMMAND_CONVERSION.toString();
		command.setCommandType(type);
	}

	@Override
	public void setResult() {
		command.setResult(null);
	}

}
