package com.app.hos.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("restriction")
public class Utils {

	private static final String propertyFilePath = "system/config.properties";
	private static Properties properties = new Properties();

	static {
		loadResources();
	}
	
	private static void loadResources() {
		InputStream stream = null;
		try {
			stream = Utils.class.getClassLoader().getResourceAsStream(propertyFilePath);
			properties.load(stream);
		} catch (IOException e) {/* maybe log to console when file not found on system start */} 
		finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {}
			}
		};
	}
	
	public static String getSystemProperty (String property) {
		return properties.getProperty(property);
	}
	
	private static OperatingSystemMXBean getOperatingSystemBean () {
		Object opearingSystemBeans = ManagementFactory.getOperatingSystemMXBean();
	    return (OperatingSystemMXBean) opearingSystemBeans;
	}
	
	public static double getCpuUsage() {
		OperatingSystemMXBean opearingSystemBeans = getOperatingSystemBean();
		return opearingSystemBeans.getProcessCpuLoad() * 100;
	}
	
	public static double getRamUsage() {
		OperatingSystemMXBean opearingSystemBeans = getOperatingSystemBean();
		long total = opearingSystemBeans.getTotalPhysicalMemorySize();
		long free = opearingSystemBeans.getFreePhysicalMemorySize();
		return (1 - (free / (double)total)) * 100;
	}
	
    public static double generateRandomDouble() { 
    	double generated = ThreadLocalRandom.current().nextDouble(0, 100);
    	generated = generated*100;
    	generated = (double)((int) generated);
		return generated /100;
	}
    
    public static int generateRandomInteger() { 
		return ThreadLocalRandom.current().nextInt();
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
