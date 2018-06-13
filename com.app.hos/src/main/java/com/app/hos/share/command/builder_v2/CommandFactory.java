package com.app.hos.share.command.builder_v2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
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
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.ReflectionUtils;

// NEED TESTING !!
@Service
@Transactional
public class CommandFactory implements AbstractMapFactory<CommandType,Class<? extends AbstractCommandBuilder>, Command> {

	@Autowired
	private CommandRepository commandRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	private CommandBuilder commandBuilder = new CommandBuilder();
	
	private final Map<CommandType, Class<? extends AbstractCommandBuilder>> builders = new LinkedHashMap<>();

	@Override
	public Command get(CommandType key) {
		Class<? extends AbstractCommandBuilder> factoryClazz = builders.get(key);
		try {
			commandBuilder.setCommandBuilder(factoryClazz.newInstance());
			return commandBuilder.createCommand();
		} catch (ReflectiveOperationException e) {
	    	return null;
		}
	}

	@Override
	public void add(CommandType key, Class<? extends AbstractCommandBuilder> value) {
		builders.put(key, value);
	}
	
	@Override
	public void register(String packageToScan) {
		List<String> factories = ReflectionUtils.scanForAnnotation(CommandDescriptor.class,packageToScan);
		for(String factory : factories) {
			try {
				Class<?> facotryClazz = ReflectionUtils.getClass(factory);
				CommandDescriptor annotation = facotryClazz.getAnnotation(CommandDescriptor.class);
				CommandType commandType = annotation.type();
				CommandTypeEntity commandTypeEntity = new CommandTypeEntity(commandType);
				DeviceType [] devicesType = annotation.device();
				for (DeviceType deviceType : devicesType) {
					DeviceTypeEntity deviceTypeEntity;
					try {
						deviceTypeEntity = deviceRepository.findType(deviceType);
						if (deviceTypeEntity == null) 
							deviceTypeEntity = new DeviceTypeEntity(deviceType);
					} catch (PersistenceException e) {
						deviceTypeEntity = new DeviceTypeEntity(deviceType);
					}
					deviceTypeEntity.addCommandType(commandTypeEntity);
					commandTypeEntity.addDeviceType(deviceTypeEntity);
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
