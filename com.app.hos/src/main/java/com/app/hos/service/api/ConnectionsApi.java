package com.app.hos.service.api;

public interface ConnectionsApi {
	
	public boolean closeConnection(String connectionId);
	
	public boolean isConnectionOpen (String connectionId);

}
