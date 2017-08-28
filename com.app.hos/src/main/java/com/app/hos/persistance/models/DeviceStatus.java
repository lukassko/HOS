package com.app.hos.persistance.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.hos.share.utils.DateTime;

@Entity
@Table(name = "devices_statuses")
public class DeviceStatus extends BaseEntity implements Comparable<DeviceStatus> {

	private DateTime time;

    private double ramUsage;

    private double cpuUsage;
    
    public DeviceStatus(DateTime time, double ramUsage, double cpuUsage) {
		this.time = time;
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

	public int compareTo(DeviceStatus status) {
		long thisTimestamp = this.time.getTimestamp();
		long objectTimestamp = status.getTime().getTimestamp();
		return (thisTimestamp < objectTimestamp ) ? -1: (thisTimestamp > objectTimestamp) ? 1:0;
	}
	
	@Override
	public String toString() {
		return "DeviceStatus [time=" + time + ", ramUsage=" + ramUsage + ", cpuUsage=" + cpuUsage + "]";
	}

}
