package com.app.hos.service.command.decorators;

import java.util.concurrent.Callable;

import com.app.hos.service.command.CommandInfo;

public abstract class FutureCommandDecorator implements Callable<CommandInfo> {

	protected CommandInfo commandInfo;
	
	public FutureCommandDecorator(CommandInfo commandInfo) {
		this.commandInfo = commandInfo;
	}

	public CommandInfo getCommand() {
		return commandInfo;
	}

	public void setCommand(CommandInfo commandInfo) {
		this.commandInfo = commandInfo;
	}
}
