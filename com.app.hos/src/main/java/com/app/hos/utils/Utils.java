package com.app.hos.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.hibernate.type.SerializationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	
    public static String getJsonObject(Object object) {
    	if (object == null) {
            throw new IllegalArgumentException("Object to serialization cannot be null");
        }
    	ObjectMapper mapper = new ObjectMapper(); 
		String devicesAsJson;
		try {
			devicesAsJson = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			devicesAsJson = "";
		}
        return devicesAsJson;
    }

    public static Object deserialize(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        ObjectInputStream in = null;
        try {
            // stream closed in the finally
            in = new ObjectInputStream(inputStream);
            return in.readObject();
            
        } catch (ClassNotFoundException ex) {
            throw new SerializationException("Exception during deserialization",ex);
        } catch (IOException ex) {
            throw new SerializationException("Exception during deserialization", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore
            }
        }
    }
}
