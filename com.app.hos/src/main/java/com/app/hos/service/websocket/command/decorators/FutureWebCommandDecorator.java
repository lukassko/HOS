package com.app.hos.service.websocket.command.decorators;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.hos.service.SystemFacade;
import com.app.hos.service.websocket.command.builder.WebCommand;

public abstract class FutureWebCommandDecorator implements Callable<WebCommand> {
	
	@Autowired
	protected SystemFacade systemFacade;
	
	protected WebCommand command;

	public FutureWebCommandDecorator(WebCommand command) {
		this.command = command;
	}

	public WebCommand getCommand() {
		return command;
	}

	public void setCommand(WebCommand command) {
		this.command = command;
	}

}
