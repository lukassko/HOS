package com.app.hos.share.command.decorators;

import java.util.concurrent.Callable;

import com.app.hos.share.command.builder_v2.Command;

public abstract class FutureCommandDecorator implements Callable<Command> {

	protected Command command;
	
	public FutureCommandDecorator(Command command) {
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}
