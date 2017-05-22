package com.app.hos.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {


	
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

}
