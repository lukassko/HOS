package com.app.hos.utils.json;

import java.io.IOException;

import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.json.deserializers.DateTimeJsonDeserializer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JsonConverter {
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static SimpleModule module = new SimpleModule();
	
    public static String getJson(Object object) {
    	if (object == null) {
            throw new IllegalArgumentException();
        }
    	String json = null;
        try {
        	json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return json;
    }

    public static <T> String getJson(T object, Class<T> clazz, StdSerializer<T> serializer) {
    	String json = null;
    	module.addSerializer(clazz, serializer);
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.registerModule(module);
    	try {
    		json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return json;
    }
    
    public static <T> T getObject(String json, Class<T> clazz) {
    	T obj = null;
    	try {
			obj = mapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return obj;
    }
    
    public static <T> T getObject(String json, Class<T> clazz, StdDeserializer<T> deserializer) {
    	T obj = null;
    	module.addDeserializer(DateTime.class, new DateTimeJsonDeserializer(DateTime.class));
        mapper.registerModule(module);
        try {
        	obj = mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return obj;
    }
     
    
}
