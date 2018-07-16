package com.app.hos.server.messaging;

import org.springframework.messaging.Message;

public interface InboundMessageMapper<T> {

	Message<?> toMessage(T object) throws Exception;
}
