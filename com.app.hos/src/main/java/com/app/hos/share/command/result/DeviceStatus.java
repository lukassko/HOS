package com.app.hos.share.command.result;

import java.io.Serializable;

import com.app.hos.share.utils.DateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceStatus implements Result,Serializable {

	@JsonIgnore
	private static final long serialVersionUID = 2L;
	
	private DateTime time;
	@JsonProperty("ram")
    private double ramUsage;
	@JsonProperty("cpu")
    private double cpuUsage;
    
	public DeviceStatus( double ramUsage, double cpuUsage) {
		this.time = new DateTime();
		this.ramUsage = ramUsage;
		this.cpuUsage = cpuUsage;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	public double getRamUsage() {
		return ramUsage;
	}

	public void setRamUsage(double ramUsage) {
		this.ramUsage = ramUsage;
	}

	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
    
}
