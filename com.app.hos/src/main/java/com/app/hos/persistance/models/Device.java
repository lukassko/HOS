package com.app.hos.persistance.models;

import javax.persistence.Transient;

import org.joda.time.DateTime;

public class Device extends BaseEntity {

	@Transient 
	private String connectionId;
	
	private String hostname;
	
	private String ip;
	
	private String remotePort;
	
	private DateTime connectionTime;
	
	public Device(String connectionId, String name, String ip, String port, DateTime connectionTime) {
		this.connectionId = connectionId;
		this.hostname = name;
		this.ip = ip;
		this.remotePort = port;
		this.connectionTime = connectionTime;
	}
	
	public String getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(String serial) {
		this.connectionId = serial;
	}
	public String getName() {
		return hostname;
	}
	public void setName(String name) {
		this.hostname = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return remotePort;
	}
	public void setPort(String port) {
		this.remotePort = port;
	}
	public DateTime getConnectionTime() {
		return connectionTime;
	}
	public void setConnectionTime(DateTime connectionTime) {
		this.connectionTime = connectionTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connectionTime == null) ? 0 : connectionTime.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((remotePort == null) ? 0 : remotePort.hashCode());
		result = prime * result + ((connectionId == null) ? 0 : connectionId.hashCode());
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
		Device other = (Device) obj;
		if (connectionTime == null) {
			if (other.connectionTime != null)
				return false;
		} else if (!connectionTime.equals(other.connectionTime))
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (remotePort == null) {
			if (other.remotePort != null)
				return false;
		} else if (!remotePort.equals(other.remotePort))
			return false;
		if (connectionId == null) {
			if (other.connectionId != null)
				return false;
		} else if (!connectionId.equals(other.connectionId))
			return false;
		return true;
	}
	
	
}
