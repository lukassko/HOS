package com.app.hos.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.app.hos.service.exceptions.handler.ExceptionUtils;

public class TcpServer  {
	
	private final int serversThredsCount = 4;
	
	private ServerSocket server;
	
	private int port;
	
	private volatile boolean isStopped = false;
	
	private final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(serversThredsCount);
	
	private Map<String,Connection> activeConnections = new HashMap<>();
	
	public TcpServer(int port) {
		this.port = port;
	}

	public void start() throws IOException {
		this.server = new ServerSocket(port);
		Thread serverThread = new Thread(new ServerThread());
		serverThread.start();
	}

    public void stop() {
        this.isStopped = true;
        try {
			this.server.close();
		} catch (IOException e) {
			ExceptionUtils.handle(e);
		}
    }
    
	private boolean isStopped() {
        return this.isStopped;
    }

	private class ServerThread implements Runnable {
		
		@Override
		public void run() {
			while (!isStopped()) {
	            try {
					Socket clientSocket = server.accept();
					Connection connection = new Connection(clientSocket);
					activeConnections.put(connection.getConnectionId(), connection);
					clientProcessingPool.submit(new MessageWorker(connection));
				} catch (IOException e) {
					if (isStopped()) {
						break;
					}
					ExceptionUtils.handle(e);
				}
	        }
			clientProcessingPool.shutdown();
		}
	}

	
}
