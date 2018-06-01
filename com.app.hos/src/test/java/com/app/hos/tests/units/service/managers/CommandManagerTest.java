package com.app.hos.tests.units.service.managers;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.api.CommandsApi;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.decorators.FutureCommandDecorator;
import com.app.hos.share.command.decorators.GetStatusCommand;
import com.app.hos.share.command.future.FutureCommandFactory;
import com.app.hos.share.command.type.CommandType;

@RunWith(MockitoJUnitRunner.class)
public class CommandManagerTest {

	@InjectMocks
	private CommandManager manager = new CommandManager();
	
	@Mock
	private CommandsApi commandsApi;
	
	@Mock
	private FutureCommandFactory futureCommandFactory;

	@Test
	public void executeCommandManagerWithHelloCommandShouldCallSendCommandMethod() {
		// given
		String connectionId = "connection_id";
		Command command = new Command();
		command.setCommandType(CommandType.MY_STATUS.name());
		FutureCommandDecorator futureCommand = new GetStatusCommand(command);
		
		doNothing().when(commandsApi).sendCommand(connectionId,command);
		doReturn(futureCommand).when(futureCommandFactory).get(command);

		// when
		manager.executeCommand(connectionId, command);

		// then
		verify(commandsApi,timeout(2000)).sendCommand(connectionId, command);
		
		DeviceStatus status = (DeviceStatus)command.getResult();
		assertTrue(status.getCpuUsage() >= 0);
		assertTrue(status.getCpuUsage() <= 100);
		
		assertTrue(status.getRamUsage() >= 0);
		assertTrue(status.getRamUsage() <= 100);
	}

}
