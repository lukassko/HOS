package com.app.hos.share.command.builder;

import com.app.hos.share.command.builder.Result;

import java.io.Serializable;

public class ResultUsage implements Result,Serializable {

	private static final long serialVersionUID = 1L;
	
	private UsageType type;
    private double usage;

    public enum UsageType {
        CPU,RAM,
    }

    public ResultUsage (UsageType type, double usage) {
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
