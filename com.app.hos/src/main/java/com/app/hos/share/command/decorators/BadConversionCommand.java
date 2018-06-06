package com.app.hos.share.command.decorators;

import com.app.hos.share.command.CommandInfo;
import com.app.hos.share.command.future.FutureCommand;
import com.app.hos.share.command.type.CommandType;

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
