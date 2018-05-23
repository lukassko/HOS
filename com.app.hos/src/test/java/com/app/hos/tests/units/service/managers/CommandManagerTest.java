package com.app.hos.tests.units.service.managers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.aopalliance.intercept.Invocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.hos.service.api.CommandsApi;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.decorators.FutureCommandDecorator;
import com.app.hos.share.command.decorators.GetStatusCommand;
import com.app.hos.share.command.future.FutureCommandFactory;
import com.app.hos.share.command.type.CommandType;

public class CommandManagerTest {

	@Autowired
	@InjectMocks
	private CommandManager manager;
	
	@Mock
	private CommandsApi commandsApi;
	
	@Mock
	private FutureCommandFactory futureCommandFactory;

	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void executeCommandManagerWithHelloCommandShouldCallSendCommandMethod() {
		// given
		String connectionId = "connection_id";
		Command command = new Command();
		command.setCommandType(CommandType.MY_STATUS.name());
		FutureCommandDecorator futureCommand = new GetStatusCommand(command);
		
		Mockito.doNothing().when(commandsApi).sendCommand(connectionId,command);
		Mockito.doReturn(futureCommand).when(futureCommandFactory).get(command);
		final CountDownLatch finished = new CountDownLatch(1);
		Mockito.doAnswer(invocation -> {
				finished.countDown();
				return null;
			}).when(commandsApi).sendCommand(connectionId, command);
		
		// when
		manager.executeCommand("some_serial_id", command);
		boolean ended;
		try {
			ended = finished.await(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			ended = false;
		}
		
		// then
		Assert.assertTrue(ended);
		Mockito.verify(commandsApi, Mockito.times(1)).sendCommand(connectionId,command);
	}

}
