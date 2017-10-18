package com.app.hos.share.command.builder;

import java.util.concurrent.Callable;

import com.app.hos.share.command.executable.GetStatusCommand;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.exceptions.NotExecutableCommand;

public class CommandConverter {

	public static Callable<Command> getExecutableCommand(Command command) throws NotExecutableCommand {
		CommandType type = CommandType.valueOf(command.getCommandType());
		Callable<Command> executableCommand = null; 
		if (type == CommandType.GET_STATUS) {
			executableCommand = (GetStatusCommand)command; 
		} else {
			throw new NotExecutableCommand(type);
		}
		return executableCommand;
	}
}
