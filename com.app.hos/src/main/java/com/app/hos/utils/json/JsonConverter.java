package com.app.hos.utils.json;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.json.deserializers.DateTimeJsonDeserializer;
import com.app.hos.utils.json.serializers.DateTimeJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SuppressWarnings("rawtypes")
public class JsonConverter {
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static SimpleModule module = new SimpleModule();
	
	static {
		module.addSerializer(DateTime.class, new DateTimeJsonSerializer(DateTime.class));
    	module.addDeserializer(DateTime.class, new DateTimeJsonDeserializer(DateTime.class));
		mapper.registerModule(module);
	}
	
    public static String getJson(Object object) {
    	String json = null;
        try {
        	json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return json;
    }

    public static String getJson(Map map) {
    	ArrayNode arrayNode = mapper.createArrayNode();
    	Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
        	ObjectNode node = mapper.createObjectNode();
            Map.Entry pair = (Map.Entry)it.next();
            Object key = pair.getKey();
            Object value = pair.getValue();
            Field[] keyFields = key.getClass().getDeclaredFields();
            Field[] valueFields = value.getClass().getDeclaredFields();
            ObjectNode keyNode = mapper.createObjectNode();
            for(Field field : keyFields){
            	//keyNode.put(field.getName(), getJson(field,key));
				keyNode.putPOJO(field.getName(), getPOJOFromField(field, key));
            }
            ObjectNode valueNode = mapper.createObjectNode();
            for(Field field : valueFields){
            	//valueNode.put(field.getName(), getJson(field,value));
				valueNode.putPOJO(field.getName(), getPOJOFromField(field, value));
            }
            node.set(getClassName(key), keyNode);
        	node.set(getClassName(value), valueNode);
            arrayNode.add(node);
        }
        String jsonFinal = null;
		try {
			jsonFinal = mapper.writeValueAsString(arrayNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return jsonFinal;
    }
        
    private static Object getPOJOFromField (Field field, Object object) {
    	field.setAccessible(true);
    	Object fieldObject = null;
    	try {
			fieldObject = field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return fieldObject;
    	
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
         
    public static String readField(String json, String name){
    	String value = null;
		try {
			ObjectNode object = new ObjectMapper().readValue(json, ObjectNode.class);
			JsonNode node = object.get(name);
	        value = (node == null ? null : node.textValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return value;
      }

    private static String getClassName(Object object) {
    	String name = object.getClass().getSimpleName();
    	return name.toLowerCase();
    }
    
    private static ObjectNode getNullNode() {
    	ObjectNode node = mapper.createObjectNode();
    	
    	return null;
    }
}
