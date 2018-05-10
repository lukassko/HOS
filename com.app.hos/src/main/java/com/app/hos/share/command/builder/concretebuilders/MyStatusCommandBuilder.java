package com.app.hos.share.command.builder.concretebuilders;


import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.type.CommandType;

public class MyStatusCommandBuilder extends AbstractCommandBuilder {

	@Override
	public AbstractCommandBuilder setCommandType() {
		String type = CommandType.MY_STATUS.toString();
		command.setCommandType(type);
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
