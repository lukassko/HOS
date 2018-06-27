package com.app.hos.share.command.future;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.share.command.CommandInfo;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.decorators.FutureCommandDecorator;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.ReflectionUtils;

@Service
public class FutureCommandFactory 
		implements AbstractMapFactory<Object,String, Callable<CommandInfo>> {
	
	private final Map<CommandType, String> beans = new LinkedHashMap<>();
	
	@Override
	public Callable<CommandInfo> get(Object command) {
		if (!(command instanceof CommandInfo))
			throw new IllegalArgumentException("Expected Command instance but was " + command.getClass().getName());
		
		CommandInfo cmdInfo = (CommandInfo) command;
		Command cmd = cmdInfo.getCommand();
		CommandType type = cmd.getCommandType(); 
		String beanName = beans.get(type);
		
		if (beanName == null) {
			throw new NotExecutableCommandException(type);
		}
		
		Object obj = ReflectionUtils.getObjectFromContext(beanName,command);
		return (FutureCommandDecorator)obj;
	}

	@Override
	public void add(Object key, String value) {
		if (!(key instanceof CommandType))
			throw new IllegalArgumentException("Expected CommandType instance but was " + key.getClass().getName());
		
		CommandType type = (CommandType) key;
		beans.put(type, value);
	}

	@Override
	public void register(String path) {
		List<String> commands =  ReflectionUtils.scanForAnnotation(FutureCommand.class,path);
		
		for(String command : commands) {
			try {
				Class<?> clazz = ReflectionUtils.getClass(command);
				FutureCommand annotation = clazz.getAnnotation(FutureCommand.class);
				CommandType type = annotation.type();
				String beanName = clazz.getSimpleName();
				ReflectionUtils.addObjectToContext(clazz, "prototype");
				add(type,beanName);
			} catch (BeansException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
