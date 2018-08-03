package com.app.hos.service.command.decorators;

import com.app.hos.service.command.CommandInfo;
import com.app.hos.service.command.future.FutureCommand;
import com.app.hos.service.command.type.CommandType;

/*
 * send information about bad command serialization
 */
@FutureCommand(type = CommandType.BAD_CONVERSION)
public class BadConversionCommand extends FutureCommandDecorator {
	
	public BadConversionCommand(CommandInfo commandInfo) {
		super(commandInfo);
	}
	
	public CommandInfo call() throws Exception {
		commandInfo.getCommand().setStatus(false);
		return commandInfo;
	}

}
