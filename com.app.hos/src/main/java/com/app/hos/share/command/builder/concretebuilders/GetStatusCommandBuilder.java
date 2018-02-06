package com.app.hos.share.command.builder.concretebuilders;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.type.CommandType;

public class GetStatusCommandBuilder extends AbstractCommandBuilder {

	@Override
	public void setCommandType() {
		String type = CommandType.GET_STATUS.toString();
		command.setCommandType(type);
	}

	@Override
	public void setResult() {}
	
	@Override
	public void setStatus() {
		command.setStatus(true);
	}

}
