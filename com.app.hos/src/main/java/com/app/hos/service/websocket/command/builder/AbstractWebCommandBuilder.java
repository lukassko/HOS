package com.app.hos.service.websocket.command.builder;

public abstract class AbstractWebCommandBuilder {

	protected WebCommand command;
	protected String exceptionMessage = null;
	
	public AbstractWebCommandBuilder() {}
	
	public AbstractWebCommandBuilder(String message) {
		this.exceptionMessage = message;
	}
	
    public WebCommand getCommand() {
        return this.command;
    }
    
	public void createCommand () {
		this.command = new WebCommand();
	}
	
	public abstract void setCommandType ();
	
	public void setMessage () {
		this.command.setMessage(this.exceptionMessage);
	};

}
