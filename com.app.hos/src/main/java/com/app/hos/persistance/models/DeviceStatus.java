package com.app.hos.persistance.models;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.app.hos.share.command.result.Result;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.converters.DateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


// PREAPARE NULL OBJECT FOR DeviceStatus

@Entity
@Table(name = "devices_statuses")
public class DeviceStatus extends BaseEntity implements Comparable<DeviceStatus>,Result,Serializable {

	@JsonIgnore
	@Transient
	private static final long serialVersionUID = 2L;
	
	@NotEmpty
	@Column(name="time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@NotEmpty
	@JsonProperty("ram")
	@Column(name="ram")
    private double ramUsage;

	@NotEmpty
	@JsonProperty("cpu")
	@Column(name="cpu")
    private double cpuUsage;
    
	public DeviceStatus() {}
	
	public DeviceStatus(double ramUsage, double cpuUsage) {
		this.time = new Date();
		this.ramUsage = ramUsage;
		this.cpuUsage = cpuUsage;
	}

	
    public DeviceStatus(DateTime time, double ramUsage, double cpuUsage) {
		this.time = new Timestamp(time.getTimestamp());
		this.ramUsage = ramUsage;
		this.cpuUsage = cpuUsage;
	}

	public DateTime getTime() {
		return DateTimeConverter.getDateTime(time);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cpuUsage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(ramUsage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceStatus other = (DeviceStatus) obj;
		if (Double.doubleToLongBits(cpuUsage) != Double.doubleToLongBits(other.cpuUsage))
			return false;
		if (Double.doubleToLongBits(ramUsage) != Double.doubleToLongBits(other.ramUsage))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		//} else if (this.getTime().getTimestamp().longValue() != other.getTime().getTimestamp().longValue())
		} else if (!this.getTime().equals(other.getTime()))
			return false;
		return true;
	}

//	public void setTime(DateTime time) {
//		this.time = DateTimeConverter.getDate(time);
//	}

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
		long thisTimestamp = this.getTime().getTimestamp();
		long objectTimestamp = status.getTime().getTimestamp();
		return (thisTimestamp < objectTimestamp ) ? -1: (thisTimestamp > objectTimestamp) ? 1:0;
	}
	
	@Override
	public String toString() {
		return "DeviceStatus [time=" + time + ", ramUsage=" + ramUsage + ", cpuUsage=" + cpuUsage + "]";
	}

}
