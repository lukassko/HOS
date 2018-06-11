package com.app.hos.tests.units.share.commands;

import static org.mockito.Mockito.doNothing;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.CommandRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.AbstractMapFactory;
import com.app.hos.share.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.builder_v2.CommandFactory;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;

public class CommandFactoryTest {

	@InjectMocks
	private AbstractMapFactory< CommandType, 
								Class<? extends AbstractCommandBuilder>, 
								Command> commandFactory = new CommandFactory();
	
	@Mock
	private DeviceRepository deviceRepository;
	
	@Mock
	private CommandRepository commandRepository;
	
	@Before
	public void setUpMocks() throws ServletException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void registerMethodShouldFindPossibleCommandsAndCallRepositoriees() {
		// given
		// when
		doNothing().when(commandRepository).save(any(CommandTypeEntity.class));
		doNothing().when(deviceRepository).save(any(DeviceTypeEntity.class));
		doThrow(new NoResultException()).when(deviceRepository).findType(any(DeviceType.class));
		
		commandFactory.register("com.app.hos.share.command.builder_v2.concretebuilders");
		
		// then
		verify(commandRepository, Mockito.atLeastOnce()).save(any(CommandTypeEntity.class));
		verify(deviceRepository, Mockito.atLeastOnce()).findType(any(DeviceType.class));
	}
}
