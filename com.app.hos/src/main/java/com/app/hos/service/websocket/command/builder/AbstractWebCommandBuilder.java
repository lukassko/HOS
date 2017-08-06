package com.app.hos.service.websocket.command.builder;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.hos.service.managers.device.DeviceManager;

public abstract class AbstractWebCommandBuilder {

	@Autowired
	protected DeviceManager deviceManager;
	
	protected WebCommand command;

    public WebCommand getCommand() {
        return this.command;
    }
    
	public void createCommand () {
		this.command = new WebCommand();
	}
	
	public abstract void setCommandType ();
	
	public abstract void setMessage ();
}
