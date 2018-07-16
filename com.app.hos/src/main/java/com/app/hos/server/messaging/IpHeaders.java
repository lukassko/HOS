package com.app.hos.server.messaging;

public class IpHeaders {

	private static final String IP = "ip_";
	
	public static final String IP_ADDRESS = IP + "address";
	
	public static final String PORT = IP + "port";
	
	public static final String REMOTE_PORT = IP + "remotePort";
	
	public static final String CONNECTION_ID = IP + "connectionId";
	
	public static final String LOCAL_ADDRESS = IP + "localInetAddress";
	
	private IpHeaders() {}
}
