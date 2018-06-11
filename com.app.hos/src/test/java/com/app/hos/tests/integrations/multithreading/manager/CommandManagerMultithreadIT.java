package com.app.hos.tests.integrations.multithreading.manager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.config.repository.SqlitePersistanceConfig;
import com.app.hos.service.api.SystemFacade;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.share.command.CommandInfo;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.builder_v2.CommandFactory;
import com.app.hos.share.command.type.CommandType;

@Ignore("run only one integration test")
@WebAppConfiguration 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class, ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandManagerMultithreadIT {
	
	@InjectMocks
	@Autowired
	private CommandManager commandManager;
	
	@Mock
	private SystemFacade systemFacade;
	
	@Mock
	private CommandFactory commandFactory;
	
	
	@Before
    public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
		Mockito.doNothing().when(systemFacade).sendCommand(Mockito.anyString(), (Mockito.any(Command.class)));
    }
	

	@Test
	public void stage00_executeGetStatusCommandShouldReturnMyStatusCommand () throws NotExecutableCommandException {

		CountDownLatch finished = prepareTestWithCountDownLatch(1);

		Command command = commandFactory.get(CommandType.GET_STATUS);
		CommandInfo cmdInfo = new CommandInfo("connection_id", command);
		commandManager.executeCommand(cmdInfo);

		boolean ended;
		try {
			ended = finished.await(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			ended = false;
		}

		Assert.assertTrue(ended);
		ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
		Mockito.verify(systemFacade, Mockito.times(1)).sendCommand(Mockito.anyString(), commandCaptor.capture());
		Command sendCommand = commandCaptor.getValue();
		Assert.assertTrue(sendCommand.getEnumeratedCommandType()==CommandType.MY_STATUS);
	}
	
	@Test(expected = NotExecutableCommandException.class)
	public void stage10_tryToExecuteNotExecutableCommandShouldThrowExecption() throws NotExecutableCommandException {
		Command command = commandFactory.get(CommandType.HELLO);
		CommandInfo cmdInfo = new CommandInfo("connection_id", command);
		commandManager.executeCommand(cmdInfo);
	}
	
	@Test
	public void stage20_executeTwoCommandsShouldSendProperCommandAsResponse() throws NotExecutableCommandException {

		CountDownLatch finished = prepareTestWithCountDownLatch(2);
		
		Command command1 = commandFactory.get(CommandType.GET_STATUS);
		Command command2 = commandFactory.get(CommandType.GET_STATUS);
		CommandInfo cmdInfo1 = new CommandInfo("connection_id", command1);
		CommandInfo cmdInfo2 = new CommandInfo("connection_id", command2);
		commandManager.executeCommand(cmdInfo1);
		commandManager.executeCommand(cmdInfo2);
		
		boolean ended;
		try {
			ended = finished.await(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			ended = false;
		}

		Assert.assertTrue(ended);
		ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
		Mockito.verify(systemFacade, Mockito.times(2)).sendCommand(Mockito.anyString(), commandCaptor.capture());
		
		List<Command> commands = commandCaptor.getAllValues();
		Assert.assertTrue(commands.size() == 2);
		Assert.assertTrue(commandCaptor.getValue().getEnumeratedCommandType()==CommandType.MY_STATUS);
	}

	private CountDownLatch prepareTestWithCountDownLatch(int commandsAmount) {
		
		final CountDownLatch finished = new CountDownLatch(1);

		Mockito.doAnswer(new Answer<Object>() {
			
			public Object answer(InvocationOnMock invocation) throws Throwable {
				finished.countDown();
			    return null;
			}
			
		}).when(systemFacade).sendCommand(Mockito.anyString(), (Mockito.any(Command.class)));
		
		return finished;
	}

}
