package com.app.hos.share.command.result;

import java.io.Serializable;

public class DeviceStatus implements Result,Serializable {

	private static final long serialVersionUID = 1L;
	
	private UsageType type;
    private double usage;

    public enum UsageType {
        CPU,RAM,
    }

    public DeviceStatus (UsageType type, double usage) {
        this.type = type;
        this.usage = usage;
    }

    public UsageType getType() {
        return type;
    }

    public void setType(UsageType type) {
        this.type = type;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }
}
