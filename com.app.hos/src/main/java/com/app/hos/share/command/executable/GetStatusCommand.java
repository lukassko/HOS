package com.app.hos.share.command.executable;

import java.io.Serializable;
import java.util.concurrent.Callable;

import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.type.CommandType;

public class GetStatusCommand extends Command implements Callable<Command>, Serializable {

	private static final long serialVersionUID = 1L;


	public Command call() throws Exception {
		try {
			Thread.sleep(600); // simulate action
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Command command = CommandFactory.getCommand(CommandType.MY_STATUS);
		command.setResult(new DeviceStatus(12.6, 12.3));
		return command;
	}

}
