package com.app.hos.share.command.builder;

import com.app.hos.share.command.builder.concretebuilders.BadConversionCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.ExecutionExceptionCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.HelloCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.MyStatusCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.UnknownCommandBuilder;
import com.app.hos.share.command.type.CommandType;

public class CommandFactory {
	
	private static CommandBuilder commandBuilder = new CommandBuilder();
	
	public static Command getCommand(CommandType type) {
		if(type == null) {
			throw new IllegalArgumentException();
		}
		
		if (type == CommandType.HELLO) {
			commandBuilder.setCommandBuilder(new HelloCommandBuilder());
		} else if (type == CommandType.MY_STATUS) {
			commandBuilder.setCommandBuilder(new MyStatusCommandBuilder());
		} else if (type == CommandType.EXECUTION_EXCEPTION) {
			commandBuilder.setCommandBuilder(new ExecutionExceptionCommandBuilder());
		} else if (type == CommandType.BAD_COMMAND_CONVERSION) {
			commandBuilder.setCommandBuilder(new BadConversionCommandBuilder());
		} else  {
			commandBuilder.setCommandBuilder(new UnknownCommandBuilder());
		}
		commandBuilder.createCommand();
 		return commandBuilder.getCommand();
	}
	
}
