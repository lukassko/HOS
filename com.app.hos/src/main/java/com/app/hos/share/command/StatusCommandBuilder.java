package com.app.hos.share.command;


import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.type.CommandType;

public class StatusCommandBuilder extends AbstractCommandBuilder {

	@Override
	public void setCommandType() {
		String type = CommandType.MY_STATUS.toString();
		command.setCommandType(type);
	}

	@Override
	public void setResult() {
		command.setResult(getResult());
	}
	
	private Result getResult() {
		return new DeviceStatus(12.6, 12.3);
	}
}
