package com.app.hos.tests.integrations.multithreading.manager;

import java.util.LinkedList;
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
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.service.api.CommandsApi;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.share.command.CommandInfo;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.builder_v2.CommandFactory;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.tests.utils.MultithreadExecutor;

@Ignore("run only one integration test")
@WebAppConfiguration 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandManagerMultithreadIT {
	
	@InjectMocks
	@Autowired
	private CommandManager commandManager;
	
	@Mock
	private CommandsApi commandsApi;
	
	@Autowired
	private CommandFactory commandFactory;
	
	@Before
    public void initMocks(){
		MockitoAnnotations.initMocks(this);
		doNothing().when(commandsApi).sendCommand(any(CommandInfo.class));
    }
	
	@Test
	public void test10_executeGetStatusCommandShouldReturnSendStatusCommand ()  {
		CountDownLatch finished = prepareTestWithCountDownLatch(1);
		
		// given
		String connectionId = "connection_id";
		Command command = commandFactory.get(CommandType.GET_STATUS);
		CommandInfo cmdInfo = new CommandInfo("connection_id", command);
		
		// when
		commandManager.executeCommand(cmdInfo);

		boolean ended;
		try {
			ended = finished.await(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			ended = false;
		}
		
		// then 
		Assert.assertTrue(ended);
		ArgumentCaptor<CommandInfo> commandCaptor = ArgumentCaptor.forClass(CommandInfo.class);
		verify(commandsApi, times(1)).sendCommand(commandCaptor.capture());
		
		CommandInfo sndCmdInfo = commandCaptor.getValue();
		Command sndCmd = sndCmdInfo.getCommand();
		
		Assert.assertEquals(connectionId, sndCmdInfo.getConnectionId());
		Assert.assertEquals(CommandType.GET_STATUS,sndCmd.getCommandType());
	}
	
	@Test
	public void test11_executeGetStatusCommandWthMultithreadExecutorShouldSendGetStatusCommand ()  {
		
		// given
		String connectionId = "connection_id";
		List<Runnable> runnables = new LinkedList<>();
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				
				Command command = commandFactory.get(CommandType.GET_STATUS);
				CommandInfo cmdInfo = new CommandInfo("connection_id", command);
				commandManager.executeCommand(cmdInfo);
			}
		};
		
		runnables.add(thread);
		
		// when 
		boolean ended;
		try {
			MultithreadExecutor.assertConcurrent(runnables,1000);
			ended = true;
		} catch (InterruptedException e) {
			ended = false;
		}
		
		// then
		Assert.assertTrue(ended);
		
		// then 

		ArgumentCaptor<CommandInfo> commandCaptor = ArgumentCaptor.forClass(CommandInfo.class);
		verify(commandsApi, timeout(1000).times(1)).sendCommand(commandCaptor.capture());
		
		CommandInfo sndCmdInfo = commandCaptor.getValue();
		Command sndCmd = sndCmdInfo.getCommand();
		
		Assert.assertEquals(connectionId, sndCmdInfo.getConnectionId());
		Assert.assertEquals(CommandType.GET_STATUS,sndCmd.getCommandType());
	}
	
	@Test
	public void test20_executeTwoCommandsShouldSendTwoCommandAsResponse() {

		// given
		List<Runnable> runnables = new LinkedList<>();
		
		Runnable thread1 = new Runnable() {
			@Override
			public void run() {
				Command command = commandFactory.get(CommandType.GET_STATUS);
				CommandInfo cmdInfo = new CommandInfo("connection_id_1", command);
				commandManager.executeCommand(cmdInfo);
			}
		};
		
		runnables.add(thread1);
		
		Runnable thread2 = new Runnable() {
			@Override
			public void run() {
				Command command = commandFactory.get(CommandType.GET_STATUS);
				CommandInfo cmdInfo = new CommandInfo("connection_id_2", command);
				commandManager.executeCommand(cmdInfo);
			}
		};
		
		runnables.add(thread2);
		
		// when 
		boolean ended;
		try {
			MultithreadExecutor.assertConcurrent(runnables,1000);
			ended = true;
		} catch (InterruptedException e) {
			ended = false;
		}

		// then
		Assert.assertTrue(ended);
		ArgumentCaptor<CommandInfo> commandCaptor = ArgumentCaptor.forClass(CommandInfo.class);
		verify(commandsApi, timeout(1000).times(2)).sendCommand(commandCaptor.capture());
		
		List<CommandInfo> sendCommands = commandCaptor.getAllValues();
		Assert.assertTrue(sendCommands.size() == 2);
		Assert.assertEquals(CommandType.GET_STATUS, commandCaptor.getValue().getCommand().getCommandType());	
	}

	private CountDownLatch prepareTestWithCountDownLatch(int commandsAmount) {
		
		final CountDownLatch finished = new CountDownLatch(commandsAmount);

		doAnswer(new Answer<Object>() {
			
			public Object answer(InvocationOnMock invocation) throws Throwable {
				finished.countDown();
			    return null;
			}
			
		}).when(commandsApi).sendCommand(any(CommandInfo.class));
		
		return finished;
	}

}
