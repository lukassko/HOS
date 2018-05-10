package com.app.hos.share.command.builder_v2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.CommandRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.AbstractMapFactory;
import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandBuilder;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.Utils;

// NEED TESTING !!
@Service
@Transactional
public class CommandFactory implements AbstractMapFactory<CommandType,Class<? extends AbstractCommandBuilder>, Command> {

	@Autowired
	private CommandRepository commandRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	private CommandBuilder commandBuilder = new CommandBuilder();
	
	private final Map<CommandType, Class<? extends AbstractCommandBuilder>> factories = new LinkedHashMap<>();

	@Override
	public synchronized Command get(CommandType key) {
		Class<? extends AbstractCommandBuilder> factoryClazz = factories.get(key);
		try {
			commandBuilder.setCommandBuilder(factoryClazz.newInstance());
		} catch (ReflectiveOperationException e) {
	    	return null;
		}
		return commandBuilder.createCommand();
	}

	@Override
	public void add(CommandType key, Class<? extends AbstractCommandBuilder> value) {
		factories.put(key, value);
	}
	
	@Override
	public void register(String packageToScan) {
		Map<DeviceType,DeviceTypeEntity> devices = new HashMap<>();
		List<String> factories =  Utils.scanForAnnotation(CommandDescriptor.class,packageToScan);
		for(String factory : factories) {
			try {
				Class<?> facotryClazz = Utils.getClass(factory);
				CommandDescriptor annotation = facotryClazz.getAnnotation(CommandDescriptor.class);
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
					persistsEntity(deviceTypeEntity);
				}
				persistsEntity(commandTypeEntity);
			} catch (BeansException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
	private void persistsEntity(BaseEntity entity) {
		if (entity instanceof CommandTypeEntity) 
			commandRepository.save((CommandTypeEntity)entity);
		if (entity instanceof DeviceTypeEntity) 
			deviceRepository.save((DeviceTypeEntity)entity);
	}
}
