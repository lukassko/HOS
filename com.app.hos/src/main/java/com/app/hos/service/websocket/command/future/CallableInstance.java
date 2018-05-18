package com.app.hos.service.websocket.command.future;

import java.util.concurrent.Callable;

public interface CallableInstance<V> {
	
	Callable<V> getCallable(V arg);
	
}
