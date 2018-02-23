package com.app.hos.service.websocket.command.future;

import java.util.concurrent.Callable;

import com.app.hos.service.websocket.command.builder.WebCommand;

public interface WebCommandFactory {

	Callable<WebCommand> get(WebCommand command);
}
