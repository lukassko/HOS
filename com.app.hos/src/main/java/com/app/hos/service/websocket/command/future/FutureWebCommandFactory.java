package com.app.hos.service.websocket.command.future;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;

import com.app.hos.utils.Utils;

@Service
public class FutureWebCommandFactory {

	private final Map<WebCommand, WebCommandFactory> factories = new LinkedHashMap<>();
	
	public Callable<WebCommand> get(WebCommand command) {
		WebCommandFactory facotry = command.getType().create();
		return facotry.get(command);
/*		WebCommandFactory factory = factories.get(command);
		if (factory != null) {
			return factory.get(command);
		}
		return null;*/
	}

}
