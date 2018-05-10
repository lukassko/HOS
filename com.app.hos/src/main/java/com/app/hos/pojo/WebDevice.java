package com.app.hos.pojo;

import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;

/**
 * POJO class to send Device object 
 * over JSON without device statuses
 */
public class WebDevice {

	private Integer id;
	
	private String name;
	
	private String serial;

	private Connection connection;
	
	public WebDevice (Device device) {
		this.id = device.getId();
		this.name = device.getName();
		this.serial = device.getSerial();
		this.connection = device.getConnection();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}	
}