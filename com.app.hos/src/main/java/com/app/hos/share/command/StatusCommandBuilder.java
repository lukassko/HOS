package com.app.hos.share.command;

import java.util.LinkedList;
import java.util.List;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.builder.Result;
import com.app.hos.share.command.builder.ResultUsage;
import com.app.hos.share.command.builder.ResultUsage.UsageType;

public class StatusCommandBuilder extends AbstractCommandBuilder {

	@Override
	public void setCommandType() {
		String type = CommandType.MY_STATUS.toString();
		command.setCommandType(type);
	}

	@Override
	public void setResult() {
		List<Result> results = new LinkedList<Result>();
		results.add(getCpuUsage());
		results.add(getRamUsage());
		command.setResult(results);
	}
	
	private Result getCpuUsage() {
		return new ResultUsage(UsageType.RAM, 12.3);
	}
	
	private Result getRamUsage() {
		return new ResultUsage(UsageType.CPU, 67.1);
	}
}
