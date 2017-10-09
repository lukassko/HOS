package com.app.hos.service.managers.command.handler;

import java.util.concurrent.Callable;

import com.app.hos.share.command.builder.Command;

public class CommandHandler<T extends CommandExecutor> implements Callable<Command> {
	
	private T command;
	
	public CommandHandler(T command) {
		this.command = command;
	}
	
	public Command call() throws Exception {
		return command.execute();
	}

}
