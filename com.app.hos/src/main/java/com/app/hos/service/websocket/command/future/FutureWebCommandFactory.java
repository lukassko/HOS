package com.app.hos.service.websocket.command.future;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import com.app.hos.service.websocket.command.builder.WebCommand;

@Service
public class FutureWebCommandFactory {
	
	public Callable<WebCommand> get(WebCommand command) {
		WebCommandFactory facotry = command.getType().create();
		return facotry.get(command);
	}

}
