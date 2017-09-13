package com.app.hos.service.websocket.command.builder;

import org.springframework.stereotype.Component;

@Component
public class WebCommandBuilder {

	private AbstractWebCommandBuilder commandBuilder;
	
	public void setCommandBuilder(AbstractWebCommandBuilder commandBuilder) {
		this.commandBuilder = commandBuilder;
	}
	
	public WebCommand getCommand() {
        return commandBuilder.getCommand();
    }
	
	 public void createCommand () {
		 commandBuilder.createCommand();
		 commandBuilder.setCommandType();
		 commandBuilder.setMessage();
	 }
}
