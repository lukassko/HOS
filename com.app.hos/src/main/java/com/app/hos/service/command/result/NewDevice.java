package com.app.hos.service.command.result;

import java.io.Serializable;

import com.app.hos.service.command.type.DeviceType;

public class NewDevice implements Result, Serializable {

	private static final long serialVersionUID = 2L;

	private String serialId;
	
    private String name;
    
    private String type;

    private String ip;
    
    private int port;
    
    public NewDevice(String serialId, String name, DeviceType type, String ip, int port) {
    	this.serialId = serialId;
        this.name = name;
        this.type = type.toString();
        this.ip = ip;
        this.port = port;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String deviceId) {
		this.serialId = deviceId;
	}
    
    
}
