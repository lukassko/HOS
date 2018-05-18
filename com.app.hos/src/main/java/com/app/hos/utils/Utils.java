package com.app.hos.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

	private static Properties properties = new Properties();

	static {
		loadResources();
	}
	
	private static void loadResources() {
		InputStream input = null;
		try {
			input =  new FileInputStream("config.properties");
			properties.load(input);
		} catch (IOException e) {/* maybe log to console when file not found on system start */} 
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {}
			}
		};
	}
	
	public static String getSystemProperty (String property) {
		return properties.getProperty(property);
	}
	
    public static double generateRandomDouble() { 
    	double generated = ThreadLocalRandom.current().nextDouble(0, 100);
    	generated = generated*100;
    	generated = (double)((int) generated);
		return generated /100;
	}
    
    public static String getHostName() {
    	try {
    		InetAddress localMachine = java.net.InetAddress.getLocalHost();
    		return localMachine.getHostName();
		} catch (UnknownHostException e) {
			return "Unknown";
		}
    }
}
