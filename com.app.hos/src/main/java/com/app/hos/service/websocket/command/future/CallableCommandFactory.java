package com.app.hos.service.websocket.command.future;

import java.util.concurrent.Callable;

import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;

public interface CallableCommandFactory {

	Callable<WebCommand> get(WebCommand command);
}
