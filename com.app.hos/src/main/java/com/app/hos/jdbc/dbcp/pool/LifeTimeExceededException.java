package com.app.hos.jdbc.dbcp.pool;

public class LifeTimeExceededException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LifeTimeExceededException() {
        super();
    }
	
	public LifeTimeExceededException(String message) {
        super(message);
    }
}
