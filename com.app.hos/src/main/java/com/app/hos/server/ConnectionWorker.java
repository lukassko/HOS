package com.app.hos.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.app.hos.server.connection.TcpConnection;

public class ConnectionWorker implements Runnable {

	//private final CommandsApi commandsApi = ReflectionUtils.getObjectFromContext(CommandsApi.class);
	
	private final TcpConnection connection;
	
	public ConnectionWorker (TcpConnection connection) {
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
