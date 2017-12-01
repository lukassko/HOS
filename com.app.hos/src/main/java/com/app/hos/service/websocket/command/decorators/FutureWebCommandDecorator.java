package com.app.hos.service.websocket.command.decorators;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.service.websocket.command.builder.WebCommand;

public abstract class FutureWebCommandDecorator implements Callable<WebCommand> {
	
	@Autowired
	protected DeviceManager deviceManager;
	
	protected WebCommand command;
	
	public FutureWebCommandDecorator(WebCommand command) {
		this.command = command;
	}

}
