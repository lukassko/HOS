package com.app.hos.server.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

public class MessageBuilder<T> {

	private final T payload;
	
	private Map<String,Object> headers;
	
	/**
	 * Private constructor to be invoked from the static factory methods only.
	 */
	private MessageBuilder(T payload) {
		this.payload = payload;
		this.headers = new HashMap<>();
	}
	
	public static <T> MessageBuilder<T> withPayload(T payload) {
		return new MessageBuilder<T>(payload);
	}

	public MessageBuilder<T> setMessageHeaders(Map<String,Object> messageHeaders) {
		this.headers = messageHeaders;
		return this;
	}
	
	public MessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue) {
		if (!this.headers.containsKey(headerName))
			this.headers.put(headerName, headerValue);
		return this;
	}
	
	public MessageBuilder<T> addHeader(String header, Object value) {
		this.headers.put(header, value);
		return this;
	}
	
	public MessageBuilder<T> removeHeader(String header) {
		this.headers.remove(header);
		return this;
	}

	public Message<T> build () {
		return new GenericMessage<T>(this.payload, this.headers);
	}
}
