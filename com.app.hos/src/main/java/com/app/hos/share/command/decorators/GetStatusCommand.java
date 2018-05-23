package com.app.hos.share.command.decorators;

import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.future.FutureCommand;
import com.app.hos.share.command.type.CommandType;

@FutureCommand(type = CommandType.GET_STATUS)
public class GetStatusCommand extends FutureCommandDecorator {
	
	public GetStatusCommand(Command command) {
		super(command);
	}
	
	public Command call() throws Exception {
		try {
			Thread.sleep(600); // simulate action
		} catch (InterruptedException e) {
			System.out.println("ERR");
			e.printStackTrace();
		}
		//Command command = CommandFactory.getCommand(CommandType.MY_STATUS);
		//command.setResult(new DeviceStatus(12.6, 12.3));
		return command;
	}

}
