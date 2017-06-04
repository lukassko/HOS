package com.app.hos.share.command.result;

import java.io.Serializable;

public class NewDevice implements Result, Serializable {

	private static final long serialVersionUID = 2L;

	public enum DeviceType {
		SERVER,TELEPHONE,TV
	}
	
    private String name;
    
    private String type;

    public NewDevice(String name, DeviceType type) {
        this.name = name;
        this.type = type.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public DeviceType getType() {
        return DeviceType.valueOf(type);
    }

    public void setType(DeviceType type) {
        this.name = type.toString();
    }
}
