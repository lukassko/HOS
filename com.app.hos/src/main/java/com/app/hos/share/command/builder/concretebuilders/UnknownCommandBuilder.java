package com.app.hos.share.command.builder.concretebuilders;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.type.CommandType;

public class UnknownCommandBuilder extends AbstractCommandBuilder {

	@Override
	public AbstractCommandBuilder setCommandType() {
		String type = CommandType.UNKNOWN_COMMAND.toString();
		command.setCommandType(type);
		return this;
	}

	@Override
	public AbstractCommandBuilder setResult() {
		command.setResult(null);
		return this;
	}

	@Override
	public AbstractCommandBuilder setStatus() {
		command.setStatus(false);
		return this;
	}

}
