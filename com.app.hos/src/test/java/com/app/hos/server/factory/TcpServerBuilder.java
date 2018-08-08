package com.app.hos.server.factory;

import org.springframework.context.ApplicationEventPublisher;

import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.SocketFactory;
import com.app.hos.server.factory.TcpServer;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.handler.ThreadsExecutor;

public class TcpServerBuilder {

	private TcpServer server;

	public TcpServerBuilder (int port) {
		this.server = new TcpServer(port);
	}
	
	public TcpServerBuilder withConnnectionManager(ConnectionManager connectionManager) {
		this.server.setConnnectionManager(connectionManager);
		return this;
	}
	
	public TcpServerBuilder withApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.server.setApplicationEventPublisher(applicationEventPublisher);
		return this;
	}
	
	public TcpServerBuilder withSocketFactory(SocketFactory socketFactory) {
		this.server.setSocketFactory(socketFactory);
		return this;
	}
	
	public TcpServerBuilder withListener(TcpListener listener) {
		this.server.setListener(listener);
		return this;
	}
	
	public TcpServerBuilder withThreadsExecutor(ThreadsExecutor threadsExecutor) {
		this.server.setThreadsExecutor(threadsExecutor);
		return this;
	}
	
	public TcpServer build() {
		return this.server;
	}
}
