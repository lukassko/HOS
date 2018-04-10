package com.app.hos.utils.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.google.common.hash.Hashing;

public class SecurityUtils {
	
    private static SecureRandom secureRandom = new SecureRandom();
        
    public static byte[] getRandom(){
    	return getRandom(32);
    }

    public static String getRandomAsString(){
    	return bytesToString(getRandom(32));
    }
    
    public static byte[] getRandom(int size){
        byte[] salt = new byte[size];
        secureRandom.nextBytes(salt);
        return salt;
    }
      
    public static String getRandomAsString(int size){
    	return bytesToString(getRandom(size));
    }
    
    public static String bytesToString(byte[] bytes) {
    	return Base64.getEncoder().encodeToString(bytes);
    }
    
	public static String hash(String originalString)  {
		return Hashing.sha256()
				  .hashString(originalString, StandardCharsets.UTF_8)
				  .toString();
	}

	public static Map<String,String> getCookies(String rawCookie) {
		Map<String,String> cookies = new HashMap<>();
		String[] rawCookieParams = rawCookie.split(";");
		for(String rawCookieNameAndValue : rawCookieParams) {
	    	String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
	    	String key = rawCookieNameAndValuePair[0];
	    	String value;
	    	try {
	    		value = rawCookieNameAndValuePair[1];
	    	} catch(Exception e) {
	    		value = null;
	    	}
	    	cookies.put(key, value);
		} 
		return cookies;
	}
}
