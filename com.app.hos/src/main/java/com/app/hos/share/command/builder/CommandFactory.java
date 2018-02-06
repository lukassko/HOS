package com.app.hos.share.command.builder;

import com.app.hos.share.command.builder.concretebuilders.BadConversionCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.GetStatusCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.HelloCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.MyStatusCommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.UnknownCommandBuilder;
import com.app.hos.share.command.type.CommandType;

public class CommandFactory {
	
	private static CommandBuilder commandBuilder = new CommandBuilder();
	
	public static Command getCommand(CommandType type) {
		return CommandFactory.getCommand(type,null);
	}
	
	public static Command getCommand(CommandType type, String message) {
		if(type == null) {
			throw new IllegalArgumentException();
		}
		if (type == CommandType.HELLO) {
			commandBuilder.setCommandBuilder(new HelloCommandBuilder());
		} else if (type == CommandType.MY_STATUS) {
			commandBuilder.setCommandBuilder(new MyStatusCommandBuilder());
		} else if (type == CommandType.BAD_COMMAND_CONVERSION) {
			commandBuilder.setCommandBuilder(new BadConversionCommandBuilder(message));
		} else if (type == CommandType.GET_STATUS) {
			commandBuilder.setCommandBuilder(new GetStatusCommandBuilder());
		}else  {
			commandBuilder.setCommandBuilder(new UnknownCommandBuilder());
		}
		commandBuilder.createCommand();
 		return commandBuilder.getCommand();
	}
	
}
