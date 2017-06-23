package com.app.hos.persistance.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "finalised_connections")
@Entity
public class HistoryConnection extends BaseEntity {

	@Column(name="device_id",nullable=false)
	private int deviceId;
	
	@Column(name="ip",nullable=false)
	private String ip;

	@Column(name="remote_port",nullable=false)
	private int remotePort;

	@Column(name="begin_connection_time",nullable=false)
	private long beginConnectionTime;

	@Column(name="end_connection_time",nullable=false)
	private long endConnectionTime;

	
	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public long getConnectionTime() {
		return beginConnectionTime;
	}

	public void setConnectionTime(long connectionTime) {
		this.beginConnectionTime = connectionTime;
	}

	public long getEndConnectionTime() {
		return endConnectionTime;
	}

	public void setEndConnectionTime(long endConnectionTime) {
		this.endConnectionTime = endConnectionTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (beginConnectionTime ^ (beginConnectionTime >>> 32));
		result = prime * result + deviceId;
		result = prime * result + (int) (endConnectionTime ^ (endConnectionTime >>> 32));
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + remotePort;
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
		HistoryConnection other = (HistoryConnection) obj;
		if (beginConnectionTime != other.beginConnectionTime)
			return false;
		if (deviceId != other.deviceId)
			return false;
		if (endConnectionTime != other.endConnectionTime)
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (remotePort != other.remotePort)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoryConnection [deviceId=" + deviceId + ", ip=" + ip + ", remotePort=" + remotePort
				+ ", beginConnectionTime=" + beginConnectionTime + ", endConnectionTime=" + endConnectionTime + "]";
	}
	
}
