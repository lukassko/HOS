package com.app.hos.service.websocket;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.utils.json.JsonConverter;

@Service
public class DeviceWebSocket {

	private final int THREAD_COUNT = 4;
	
	@Autowired
    private SimpMessagingTemplate template;

	@Autowired
	private WebCommandManager commandManager;
	
	ExecutorService commandExecutor = Executors.newFixedThreadPool(THREAD_COUNT);
	
	private final String destination = "/topic/device-info";
	
	public DeviceWebSocket() {}

	public void receiveMessage(String message) {
		final WebCommand command = JsonConverter.getObject(message, WebCommand.class);
		System.out.println(command.toString());
		System.out.println(command.getType().toString());
		Callable<WebCommand> commandTask = new Callable<WebCommand>() {
			public WebCommand call() throws Exception {
				return commandManager.executeCommand(command);
			}
		};
		Future<WebCommand> submit = commandExecutor.submit(commandTask);
		try {
			WebCommand cmd = submit.get();
			if (cmd != null) {
				String response = JsonConverter.getJson(cmd);
				System.out.println(response);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void sendObjectOverWebsocket(Object object) {
		sendObjectOverWebsocket(destination, object);
	}
	
	private synchronized void sendObjectOverWebsocket(String destination,Object object) {
		template.convertAndSend(destination, object);
	}
	
	private void sendJsonOverWebsocket(String json) {
		sendJsonOverWebsocket(destination,json);
	}
	
	private synchronized void sendJsonOverWebsocket(String destination,String json) {
		try {
			Message<?> message = MessageBuilder.withPayload(json.getBytes("UTF-8")).build();
			template.send(destination, message);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	

	
}
