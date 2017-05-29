package com.app.hos.utils.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.json.deserializers.DateTimeJsonDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("rawtypes")
public class JsonConverter {
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static SimpleModule module = new SimpleModule();
	private static final Logger LOGGER = Logger.getLogger( JsonConverter.class.getName() );
	
    public static String getJson(Object object) {
    	String json = null;
        try {
        	json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return json;
    }

    public static <T> String getJson(T object, StdSerializer<T> serializer) {
    	String json = null;
    	Class<T> clazz = (Class) object.getClass();
    	module.addSerializer(clazz, serializer);
    	ObjectMapper mapper = new ObjectMapper(); // comment this
    	mapper.registerModule(module);
    	
    	try {
    		json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return json;
    }

    public static String getJson(Map map) {
    	LOGGER.log(Level.WARNING, "CONVERTING JSON");
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
            	keyNode.put(field.getName(), getJson(field,key));
            }
            ObjectNode valueNode = mapper.createObjectNode();
            for(Field field : valueFields){
            	valueNode.put(field.getName(), getJson(field,value));
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
    
    private static String getJson(Field field, Object object){
    	field.setAccessible(true);
    	Object fieldObject = null;
    	String json = null;
    	try {
    		fieldObject = field.get(object);
    		Class clazz = fieldObject.getClass();
    		if(clazz.isAnnotationPresent(JsonSerializer.class)) {
    			JsonSerializer serializer = (JsonSerializer) clazz.getAnnotation(JsonSerializer.class);
    			Class<?> serializerClazz = serializer.serializer();
    			Constructor<?> ctor = serializerClazz.getConstructor(Class.class);
    			StdSerializer inst = (StdSerializer) ctor.newInstance(clazz);
    			json = getJson(fieldObject, inst);
    		} else {
    			json = getJson(fieldObject);
    		}
		} catch (Exception e) {
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
