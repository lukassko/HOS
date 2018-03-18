package com.app.hos.utils.security;

import java.security.SecureRandom;

public class Salting {
	
    private static SecureRandom secureRandom = new SecureRandom();
    
    public static byte[] getRandomSalt(){
    	return getRandomSalt(32);
    }

    public static byte[] getRandomSalt(int size){
        byte[] salt = new byte[size];
        secureRandom.nextBytes(salt);
        return salt;
    }

}
