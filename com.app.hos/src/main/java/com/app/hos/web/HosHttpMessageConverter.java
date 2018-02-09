package com.app.hos.web;

import java.io.IOException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.app.hos.utils.json.JsonConverter;

public class HosHttpMessageConverter extends AbstractHttpMessageConverter<Object>{

	public HosHttpMessageConverter() {
		super(MediaType.APPLICATION_JSON_UTF8);
	}
	
	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage message)
			throws IOException, HttpMessageNotReadableException {
		System.out.println("---------- readInternal " + message.toString());
		return JsonConverter.getObject(message.getBody().toString(), clazz);
	}

	@Override
	protected boolean supports(Class<?> arg0) {
		System.out.println("---------- supports " + arg0.toString());
		return true;
	}

	protected <T> void writeWithMessageConverters(T returnValue, MethodParameter returnType,
            ServletServerHttpRequest inputMessage, ServletServerHttpResponse outputMessage)
            throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

		System.out.println("---------- writeWithMessageConverters ");
	}
	
	@Override
	protected void writeInternal(Object obj, HttpOutputMessage message)
			throws IOException, HttpMessageNotWritableException {		
		System.out.println("---------- writeInternal " + message);
		message.getBody().write(JsonConverter.getJson(obj).getBytes());
	}

}
