package com.app.hos.service.websocket.command.decorators;

import com.app.hos.service.websocket.command.builder.WebCommand;

public class RemoveDeviceWebCommand extends FutureWebCommandDecorator {
	
	public RemoveDeviceWebCommand(WebCommand command) {
		super(command);
	}
	
	// TODO:
	// - finalise connection
	public WebCommand call() throws Exception {
		
		return null;
	}

}
