package com.app.hos.server.event;

import org.springframework.context.ApplicationEvent;

import com.app.hos.server.event.source.TcpEventSource;


public abstract class TcpEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public TcpEvent(TcpEventSource source) {
		super(source);
	}

	@Override
	public String toString() {
		TcpEventSource source = (TcpEventSource)this.getSource();
		Throwable throwable = source.getThrowable();
		return "TcpEvent [source=" + source + (throwable == null ? "" : ", cause=" + throwable) + "]";
	}

}
