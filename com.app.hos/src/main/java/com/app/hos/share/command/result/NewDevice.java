package com.app.hos.share.command.result;

public class NewDevice implements Result {

	private String name;

	public NewDevice(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
