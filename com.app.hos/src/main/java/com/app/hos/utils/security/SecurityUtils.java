package com.app.hos.utils.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

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
    
	public static String hashString(String originalString)  {
		return Hashing.sha256()
				  .hashString(originalString, StandardCharsets.UTF_8)
				  .toString();
	}
}
