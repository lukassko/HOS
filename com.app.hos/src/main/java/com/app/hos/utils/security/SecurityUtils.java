package com.app.hos.utils.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.hash.Hashing;

public class SecurityUtils {
	
    private static SecureRandom secureRandom = new SecureRandom();
        
    public static HttpSession getSessionFromHttpServletRequest(HttpServletRequest request){
    	return getSessionFromHttpServletRequest(request,false);
    }

    public static HttpSession getSessionFromHttpServletRequest(HttpServletRequest request, boolean create){
    	return request.getSession(create);
    }

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

	public static void unauthorized(HttpServletResponse response, String message) throws IOException {
		response.sendError(401, message);
	}
	
}
