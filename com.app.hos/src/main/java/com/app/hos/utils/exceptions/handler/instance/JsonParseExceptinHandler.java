package com.app.hos.utils.exceptions.handler.instance;

import com.app.hos.utils.exceptions.handler.ExceptionHandler;
import com.app.hos.utils.exceptions.handler.HOSExceptionHandler;
import com.fasterxml.jackson.core.JsonParseException;

@ExceptionHandler
public class JsonParseExceptinHandler implements HOSExceptionHandler<JsonParseException>{

	@Override
	public void handle(JsonParseException throwable, boolean throwRuntimeException) {
		
	}

}

    