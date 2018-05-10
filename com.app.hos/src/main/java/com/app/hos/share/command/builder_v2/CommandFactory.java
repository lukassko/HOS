package com.app.hos.share.command.builder_v2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.models.command.DeviceTypeEntity;
import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.websocket.command.future.FutureCommand;
import com.app.hos.service.websocket.command.future.WebCommandFactory;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.share.command.builder.CommandBuilder;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.Utils;

// NEED TESTING !!
@Service
@SuppressWarnings("rawtypes")
public class CommandFactory implements AbstractMapFactory {

	private static CommandBuilder commandBuilder = new CommandBuilder();
	
	private final Map<CommandType, CommandBuilderWrapper> factories = new LinkedHashMap<>();
	
	@Override
	public Object get(Object key) {
		String stringType = (String)key;
		CommandType commandType;
		try {          
			commandType = CommandType.valueOf(stringType);
	    } catch (IllegalArgumentException e) {
	    	return null;
	    }
		CommandBuilderWrapper factory = factories.get(commandType);
		commandBuilder.setCommandBuilder(factory.getBuilderInstance());
		return commandBuilder.createCommand();
	}

	@Override
	public void add(Object key, Object value) {
		String stringType = (String)key;
		CommandType commandType;
		try {          
			commandType = CommandType.valueOf(stringType);
	    } catch (IllegalArgumentException e) {
	    	throw new RuntimeException("Invalid value for enum " + stringType);
	    }
		CommandBuilderWrapper builder = (CommandBuilderWrapper)value;
		factories.put(commandType, builder);
	}

	@Override
	public void register(String packageToScan) {
		Map<DeviceType,DeviceTypeEntity> devices = new HashMap<>();
		
		List<String> factories =  Utils.scanForAnnotation(Command.class,packageToScan);
		for(String factory : factories) {
			try {
				Class<?> facotryClazz = Utils.getClass(factory);
				Command annotation = facotryClazz.getAnnotation(Command.class);
				CommandType commandType = annotation.type();
				CommandTypeEntity commandTypeEntity = new CommandTypeEntity(commandType);
				DeviceType [] devicesType = annotation.device();
				for (DeviceType deviceType : devicesType) {
					DeviceTypeEntity deviceTypeEntity;
					if (devices.containsKey(deviceType)) {
						deviceTypeEntity = devices.get(deviceType);
					} else {
						deviceTypeEntity = new DeviceTypeEntity(deviceType);
						devices.put(deviceType, deviceTypeEntity);
					}
					deviceTypeEntity.addCommandType(commandTypeEntity);
					commandTypeEntity.addDeviceType(deviceTypeEntity);
				}
			} catch (BeansException e) {}
		}
	}
	
}
