package com.app.hos.server.messaging;

import org.springframework.messaging.Message;

public interface OutboundMessageMapper<T> {

	T fromMessage(Message<?> message) throws Exception;

}
