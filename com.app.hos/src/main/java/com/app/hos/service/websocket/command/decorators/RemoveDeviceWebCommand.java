package com.app.hos.service.websocket.command.decorators;

import com.app.hos.service.websocket.command.builder.WebCommand;

//This commands is passed through HTTP request
//DO NOT USE WEBSOCKET
public class RemoveDeviceWebCommand extends FutureWebCommandDecorator {
	
	public RemoveDeviceWebCommand(WebCommand command) {
		super(command);
	}
	
	public WebCommand call() throws Exception {
		String deviceSerial = command.getMessage();
		if (systemFacade.removeDevice(deviceSerial)) {
			command.setStatus(true);
		} else {
			command.setStatus(false);
			command.setMessage(""); // set why device cannot be removed
		}
		return command;
	}

}
