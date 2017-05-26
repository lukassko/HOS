package com.app.hos.share.command;

import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandType;

public class CommandFactory {
	
	private static CommandBuilder commandBuilder = new CommandBuilder();
	
	public static Command getCommand(CommandType type) {
		if(type == null) {
			throw new IllegalArgumentException();
		}
		
		if (type == CommandType.HELLO) {
			commandBuilder.setCommandBuilder(new HelloCommandBuilder());
		} else if (type == CommandType.MY_STATUS) {
			commandBuilder.setCommandBuilder(new StatusCommandBuilder());
		} else {
			return null;
		}
		
 		return commandBuilder.getCommand();
	}
	
}
