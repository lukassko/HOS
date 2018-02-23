package com.app.hos.service.websocket.command.future;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;

import com.app.hos.utils.Utils;

@Service
public class FutureWebCommandFactory {

	private final Map<WebCommandType, WebCommandFactory> factories = new LinkedHashMap<>();
		
	public Callable<WebCommand> get(WebCommand command) {
		WebCommandFactory factory = factories.get(command.getType());
		if (factory != null) {
			return factory.get(command);
		}
		return null;
	}
	
	public void addFactory(WebCommandType type, WebCommandFactory factory) {
		this.factories.put(type, factory);
	}
	
	public void registerFactories(String packageToScan) {
		List<String> factories =  Utils.scanForAnnotation(FutureCommand.class,packageToScan);
		for(String factory : factories) {
			try {
				Class<?> facotryClazz = Utils.getClass(factory);
				FutureCommand annotation = facotryClazz.getAnnotation(FutureCommand.class);
				WebCommandType type = annotation.type();
				WebCommandFactory facotry = (WebCommandFactory)(Utils.createObject(facotryClazz));
				addFactory(type, facotry);
			} catch (BeansException e) {}
			
		}
	}
}
