package com.app.hos.service.websocket.command.future;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.BeansException;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.decorators.FutureWebCommandDecorator;
import com.app.hos.utils.ReflectionUtils;

public class FutureWebCommandFactory 
		implements AbstractMapFactory<Object,String, Callable<WebCommand>> {
	
	private final Map<WebCommandType, String> beans = new LinkedHashMap<>();
	
	@Override
	public Callable<WebCommand> get(Object command) {
		if (!(command instanceof WebCommand))
			throw new IllegalArgumentException("Expected WebCommand instance but was " + command.getClass().getName());
		
		WebCommand cmd = (WebCommand) command;
		WebCommandType type = cmd.getType(); 
		String beanName = beans.get(type);
		Object obj = ReflectionUtils.getObjectFromContext(beanName,command);
		return (FutureWebCommandDecorator)obj;
	}

	@Override
	public void add(Object key, String value) {
		if (!(key instanceof WebCommandType))
			throw new IllegalArgumentException("Expected WebCommandType instance but was " + key.getClass().getName());
		
		WebCommandType type = (WebCommandType) key;
		beans.put(type, value);
	}

	@Override
	public void register(String path) {
		List<String> commands =  ReflectionUtils.scanForAnnotation(FutureWebCommand.class,path);
		for(String command : commands) {
			try {
				Class<?> clazz = ReflectionUtils.getClass(command);
				FutureWebCommand annotation = clazz.getAnnotation(FutureWebCommand.class);
				WebCommandType type = annotation.type();
				String beanName = clazz.getSimpleName();
				ReflectionUtils.addObjectToContext(clazz, "prototype");
				add(type,beanName);
			} catch (BeansException e) {}
		}
	}

}
