package com.app.hos.share.command.decorators;

import java.util.concurrent.Callable;

import com.app.hos.share.command.builder.Command;

public abstract class CommandDecorator implements Callable<Command> {
	
	protected Command command;
	
	public CommandDecorator(Command command) {
		this.command = command;
	}

}
