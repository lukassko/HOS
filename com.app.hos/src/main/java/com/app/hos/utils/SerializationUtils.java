package com.app.hos.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.app.hos.service.exceptions.SerializationException;

public class SerializationUtils <T extends Serializable> {

	public void serialize(T obj, OutputStream outputStream) throws SerializationException {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T deserialize(InputStream inputStream) throws SerializationException {
		try {
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			return (T)ois.readObject();
		} catch (Exception e) {
			throw new SerializationException(e.getMessage(), e);
		} 
	}
	
}
