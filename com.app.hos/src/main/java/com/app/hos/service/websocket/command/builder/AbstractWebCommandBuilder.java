package com.app.hos.service.websocket.command.builder;

public abstract class AbstractWebCommandBuilder {

	protected WebCommand command;

    public WebCommand getCommand() {
        return this.command;
    }
    
	public void createCommand () {
		this.command = new WebCommand();
	}
	
	public abstract void setCommandType ();
}
