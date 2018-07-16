package com.app.hos.server.messaging;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class MessageBuilder<T> {

	private final T payload;
	
	private MessageHeaderAccessor messageHeaderAccessor;
	
	/**
	 * Private constructor to be invoked from the static factory methods only.
	 */
	private MessageBuilder(T payload) {
		this.payload = payload;
		this.messageHeaderAccessor = new MessageHeaderAccessor();
	}
	
	public static <T> MessageBuilder<T> withPayload(T payload) {
		return new MessageBuilder<T>(payload);
	}
	
	public MessageBuilder<T> setMessageHeaders(MessageHeaders messageHeaders) {
		MessageHeaders originalMessageHeaders = messageHeaderAccessor.getMessageHeaders();
		messageHeaders.forEach(originalMessageHeaders::putIfAbsent);
		return this;
	}
	
	public MessageBuilder<T> copyHeaders(Map<String,?> headersToCopy) {
		messageHeaderAccessor.copyHeaders(headersToCopy);
		return this;
	}
	
	public MessageBuilder<T> setHeader(String headerName, Object headerValue) {
		messageHeaderAccessor.setHeader(headerName, headerValue);
		return this;
	}
	
	public MessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue) {
		messageHeaderAccessor.setHeaderIfAbsent(headerName, headerValue);
		return this;
	}
	
	public MessageBuilder<T> removeHeader(String headerName) {
		messageHeaderAccessor.removeHeader(headerName);
		return this;
	}

	public Message<T> build () {
		return new GenericMessage<T>(this.payload, this.messageHeaderAccessor.toMap());
	}
}
