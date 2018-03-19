package com.app.hos.utils.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import com.google.common.hash.Hashing;

public class SecurityUtils {
	
    private static SecureRandom secureRandom = new SecureRandom();
        
    public static byte[] getRandomSalt(){
    	return getRandomSalt(32);
    }

    public static byte[] getRandomSalt(int size){
        byte[] salt = new byte[size];
        secureRandom.nextBytes(salt);
        return salt;
    }
    	
	public static String hashString(String originalString)  {
		return Hashing.sha256()
				  .hashString(originalString, StandardCharsets.UTF_8)
				  .toString();
	}
}
