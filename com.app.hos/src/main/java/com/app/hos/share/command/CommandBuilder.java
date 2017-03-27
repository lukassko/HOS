package com.app.hos.share.command;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.Command;

public class CommandBuilder {

	private AbstractCommandBuilder commandBuilder;
	
	public void setCommandBuilder(AbstractCommandBuilder commandBuilder) {
		this.commandBuilder = commandBuilder; 
	}
	
	public Command getCommand() { 
		return commandBuilder.getCommand(); 
	}
	  
	public void createCommand () {
		String clientId = "SERVER_01";
		String clientName = "MAIN_SERVER";
		commandBuilder.createCommand(clientId, clientName);
		commandBuilder.setCommandType();
		commandBuilder.setResult();
	}
}
