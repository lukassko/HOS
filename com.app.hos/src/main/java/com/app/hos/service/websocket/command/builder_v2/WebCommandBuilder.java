package com.app.hos.service.websocket.command.builder_v2;

public class WebCommandBuilder {

	private AbstractWebCommandBuilder commandBuilder;
	
	public void setCommandBuilder(AbstractWebCommandBuilder commandBuilder) {
		this.commandBuilder = commandBuilder;
	}
	
	public WebCommand getCommand() {
        return commandBuilder.getCommand();
    }
	
	 public WebCommand createCommand () {
		 return commandBuilder.createCommand()
				 				.setCommandType()
				 				.setStatus()
				 				.setMessage()
				 				.getCommand();
	 }
}
