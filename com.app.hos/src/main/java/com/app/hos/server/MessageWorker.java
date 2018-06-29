package com.app.hos.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MessageWorker implements Runnable {

	//private final CommandsApi commandsApi = ReflectionUtils.getObjectFromContext(CommandsApi.class);
	
	private final Connection connection;
	
	public MessageWorker (Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void run() {
		Socket socket = connection.getSocket();
		InputStream input = null;
		OutputStream output = null;
		 try {
			input  = socket.getInputStream();
			output = socket.getOutputStream();
			
			// do something
			
			input.close();
			output.close();
		} catch (IOException e) {

		} finally {
            try {
            	if (input != null) input.close();
			} catch (IOException e) {}
			try {
				if (output != null) output.close();
			} catch (IOException e) {}
		}
	}
}
