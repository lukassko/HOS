package com.app.hos.tests.utils;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.decorators.FutureWebCommandDecorator;

public class WebCommandSimulation extends FutureWebCommandDecorator {
	
	public WebCommandSimulation(WebCommand command) {
		super(command);
	}
	
	public WebCommand call() throws Exception {
		Thread.sleep(3000);
		command.setMessage("Simmulation success.");
		return command;
	}
}
