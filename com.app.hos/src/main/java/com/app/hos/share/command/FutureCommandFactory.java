package com.app.hos.share.command;

import java.util.concurrent.Callable;

import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.decorators.GetAllDeviceWebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.decorators.GetStatusCommand;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.Utils;

public class FutureCommandFactory {

	public static Callable<Command> getCommand(Command command) throws NotExecutableCommandException {
		CommandType type = CommandType.valueOf(command.getCommandType());
		Callable<Command> executableCommand = null; 
		if (type == CommandType.GET_STATUS) {
			executableCommand = new GetStatusCommand(command);
		} else {
			throw new NotExecutableCommandException(type);
		}
		return executableCommand;
	}
}
