package com.app.hos.share.command.builder.concretebuilders;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.type.CommandType;

public class UnknownCommandBuilder extends AbstractCommandBuilder {

	@Override
	public void setCommandType() {
		String type = CommandType.UNKNOWN_COMMAND.toString();
		command.setCommandType(type);
	}

	@Override
	public void setResult() {
		command.setResult(null);
	}

	@Override
	public void setStatus() {
		command.setStatus(false);
	}

}
