package com.app.hos.server.event;

import com.app.hos.server.connection.SocketInfo;
import com.app.hos.server.factory.TcpServerListener;

@FunctionalInterface
public interface EventFactory {
	
	default TcpEvent create(SocketInfo socketInfo){
		return this.create(socketInfo, null);
	};
	
	default TcpEvent create(SocketInfo socketInfo, Throwable cause){
		return this.create((Object)socketInfo, cause);
	};
	
	default TcpEvent create(TcpServerListener tcpServerListener){
		return this.create(tcpServerListener, null);
	};
	
	default TcpEvent create(TcpServerListener tcpServerListener, Throwable cause){
		return this.create((Object)tcpServerListener, cause);
	};
	
	default TcpEvent create(String connectionId){
		return this.create(connectionId, null);
	};
	
	default TcpEvent create(String connectionId, Throwable cause){
		return this.create((Object)connectionId, cause);
	};
	
	TcpEvent create (Object source, Throwable cause);

}
