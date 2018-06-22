package com.app.hos.tests.integrations.multithreading.manager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.AspectConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.config.repository.SqlitePersistanceConfig;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.websocket.WebSocketManager;
import com.app.hos.service.websocket.WebSocketServerEndpoint;
import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;
import com.app.hos.tests.utils.MultithreadExecutor;
import com.app.hos.tests.utils.Utils;
import com.app.hos.tests.utils.WebCommandSimulation;
import com.app.hos.utils.json.JsonConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Ignore("run only one integration test")
@WebAppConfiguration 
//@RunWith(PowerMockRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
//@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
//@PrepareForTest(FutureWebCommandFactory.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, SqlitePersistanceConfig.class, AspectConfig.class, ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebCommandManagerMultithreadIT {
	
	@Autowired
	private FutureWebCommandFactory futureWebCommandFactory;
	
	@Autowired
	private WebSocketManager webSocketManager;

	private WebSocketServerEndpoint serverEndpoint;
	
	private static WebCommand command;
	
	private static Callable<WebCommand> testExecutableCommand;
	
	@BeforeClass
	public static void initTest() {
		command = new WebCommand();
		command.setStatus(true);	
		command.setType(WebCommandType.GET_ALL_DEVICES);
		testExecutableCommand = new WebCommandSimulation(command); 
	}
	
	@Before
    public void initMocks() throws NotExecutableCommandException {
		serverEndpoint = Mockito.spy(new WebSocketServerEndpoint(webSocketManager));
		
		MockitoAnnotations.initMocks(this);
		//PowerMockito.mockStatic(FutureWebCommandFactory.class);
		//PowerMockito.when(FutureWebCommandFactory.getCommand(Mockito.any(WebCommand.class))).thenReturn(testExecutableCommand);
		//Mockito.doReturn(testExecutableCommand).when(futureWebCommandFactory.get(Mockito.any(WebCommand.class)));

		Mockito.when(futureWebCommandFactory.get(Mockito.any(WebCommand.class))).thenReturn(testExecutableCommand);
		
		Mockito.doNothing().when(serverEndpoint).sendMessage(Mockito.any(Session.class), Mockito.anyString());
		Mockito.doCallRealMethod().when(serverEndpoint).onMessage(Mockito.any(Session.class), Mockito.anyString());
	}
	
	@Test
	public void stage00_checkIfPowerMockReturnProperObjectFromStaticFactoryMethod() throws NotExecutableCommandException {
		//Callable<WebCommand> testCommand = FutureWebCommandFactory.getCommand(command);
		Callable<WebCommand> testCommand = futureWebCommandFactory.get(command);
		Assert.assertNotNull(testCommand);
		Assert.assertTrue(testCommand == testExecutableCommand);
	}
	
	@Test
	public void stage10_executeSingleWebCommandShouldExecuteAndSendBackValueAfterDelayTime () throws NotExecutableCommandException, InstantiationException, IllegalAccessException, IOException {
		
		String message = JsonConverter.getJson(command);
		serverEndpoint.onMessage(Utils.getSessionTest(), message);
		
		CountDownLatch finished = prepareTestWithCountDownLatch(1);

		boolean ended;
		try {
			ended = finished.await(3500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			ended = false;
		}
		Assert.assertTrue(ended);

		ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(serverEndpoint, Mockito.times(1)).sendMessage(Mockito.any(Session.class), commandCaptor.capture());

		WebCommand sendedWebCommand = JsonConverter.getObject(commandCaptor.getValue(), WebCommand.class);

		Assert.assertTrue(sendedWebCommand.getType().equals(WebCommandType.GET_ALL_DEVICES));
		Assert.assertEquals(sendedWebCommand.getMessage(),"Simmulation success.");
	}
	
	
	@Test
	public void stage20_executeMultiWebCommandsShouldExecuteAndSendBackValueAfterDelayTime() throws NotExecutableCommandException, JsonParseException, JsonMappingException, IOException {

		CountDownLatch finished = prepareTestWithCountDownLatch(3);
		
		List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
				
		Callable<Void> callable = new Callable<Void>() {
			public Void call() throws Exception {
				String message = JsonConverter.getJson(command);
				serverEndpoint.onMessage(Utils.getSessionTest(), message);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				String message = JsonConverter.getJson(command);
				serverEndpoint.onMessage(Utils.getSessionTest(), message);
				return null;
			}
		};
		
		callables.add(callable);
		
		callable = new Callable<Void>() {
			public Void call() throws Exception {
				String message = JsonConverter.getJson(command);
				serverEndpoint.onMessage(Utils.getSessionTest(), message);
				return null;
			}
		};
		
		callables.add(callable);
		
		try {
			MultithreadExecutor.assertConcurrent(callables,3500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean ended;
		try {
			ended = finished.await(4000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			ended = false;
		}
		Assert.assertTrue(ended);
		
		ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(serverEndpoint, Mockito.times(3)).sendMessage(Mockito.any(Session.class), commandCaptor.capture());

		List<String> messages = commandCaptor.getAllValues();
		Assert.assertTrue(messages.size() == 3);
		WebCommand lastSendedCommand = JsonConverter.getObject(commandCaptor.getValue(), WebCommand.class);
		Assert.assertTrue(lastSendedCommand.getType() == WebCommandType.GET_ALL_DEVICES);
		Assert.assertEquals(lastSendedCommand.getMessage(),"Simmulation success.");
	}

	private CountDownLatch prepareTestWithCountDownLatch(int commandsAmount) {
		
		final CountDownLatch finished = new CountDownLatch(commandsAmount);

		Mockito.doAnswer(new Answer<Object>() {
			
			public Object answer(InvocationOnMock invocation) throws Throwable {
				finished.countDown();
			    return null;
			}
			
		}).when(serverEndpoint).sendMessage(Mockito.any(Session.class), Mockito.anyString());
		
		return finished;
	}

	
	
	
}
