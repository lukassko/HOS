package com.app.hos.utils.exceptions.handler.instance;

import org.springframework.messaging.MessageHandlingException;

import com.app.hos.utils.exceptions.handler.ExceptionHandler;
import com.app.hos.utils.exceptions.handler.HOSExceptionHandler;


@ExceptionHandler
public class MessageHandlingExceptionHandler implements HOSExceptionHandler<MessageHandlingException>{
	
	
	// Exception that indicates an error occurred during message handling (spring integration, TCP connection).
	@Override
	public void handle(MessageHandlingException throwable, boolean throwRuntimeException) {
	
	}

}

    