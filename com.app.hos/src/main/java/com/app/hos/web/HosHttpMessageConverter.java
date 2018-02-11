package com.app.hos.web;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class HosHttpMessageConverter extends AbstractHttpMessageConverter<Object>{

	public HosHttpMessageConverter() {
		super(MediaType.APPLICATION_JSON_UTF8);
	}

	@Override
	protected boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage message)
			throws IOException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeInternal(Object object, HttpOutputMessage message)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub
	}
}
