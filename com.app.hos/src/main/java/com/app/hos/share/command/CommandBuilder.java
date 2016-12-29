package com.app.hos.share.command;

abstract class CommandBuilder {

	protected Command command;
	
	public Command getCommand() {
		return this.command;
	}
	
	public void createCommand (String clientId, String clientName) {
		this.command = new Command(clientId,clientName);
	}
	
	public void setClinetName () {
		
	}
	
	public void setClientId () {
		
	}
}
