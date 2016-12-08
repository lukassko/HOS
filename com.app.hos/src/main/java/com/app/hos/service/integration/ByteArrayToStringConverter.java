package com.app.hos.service.integration;

import java.io.UnsupportedEncodingException;

import org.springframework.core.convert.converter.Converter;

public class ByteArrayToStringConverter implements Converter<byte[], String>{

	private final String charSet = "UTF-8";
			
	public String convert(byte[] bytes) {
		System.out.println("IN CONVERTER");
		try {
			return new String(bytes, this.charSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new String(bytes);
		}
	}

}
