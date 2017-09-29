package com.app.hos.utils.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.json.deserializers.DateTimeJsonDeserializer;
import com.app.hos.utils.json.serializers.DateJsonSerializer;
import com.app.hos.utils.json.serializers.DateTimeJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
		module.addSerializer(Date.class, new DateJsonSerializer(Date.class));
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
            ObjectNode keyNode = mapper.createObjectNode();
            for(Field field : keyFields){
            	if(isNotIgnored(field))
            		keyNode.putPOJO(field.getName(), getPOJOFromField(field, key));
            }
            node.set(getClassName(key), keyNode);
            if (value == null) {
            	node.set("null", null);
            } else {
            	 Field[] valueFields = value.getClass().getDeclaredFields();
            	 ObjectNode valueNode = mapper.createObjectNode();
                 for(Field field : valueFields){
                 	if(isNotIgnored(field))
     					valueNode.putPOJO(field.getName(), getPOJOFromField(field, value));
                 }
                 node.set(getClassName(value), valueNode);
            }
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
    
//    private static String getJsonFieldName(Field field) {
//    	if (isAnnotatedWith(JsonProperty.class,field)) {
//    		Annotation annotation = field.getDeclaredAnnotation(JsonProperty.class);
//    		Class clazz = annotation.annotationType();
//    		try {
//				Method annotationMethod = clazz.getMethod("value");
//				String name = annotationMethod.getName();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//    	}
//     	return null;
//    }
    
    private static boolean isNotIgnored(Field field) {
    	return isAnnotatedWith(JsonIgnore.class,field);
    }
    
    private static boolean isAnnotatedWith(Class<? extends Annotation> clazz,Field field) {
    	Annotation[] annotations = field.getDeclaredAnnotations();
    	for (Annotation annotation : annotations) {
    		if (annotation.annotationType().equals(clazz)) {
    			return false;
    		}
    	}
    	return true;
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
    	if (object == null) {
    		return "null";
    	} else {
    		String name = object.getClass().getSimpleName();
        	return name.toLowerCase();
    	}
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
    
}
