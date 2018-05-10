package com.app.hos.persistance.models.connection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.converters.DateTimeConverter;

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
	private Date beginConnectionTime;

	@Column(name="end_connection_time",nullable=false)
	private Date endConnectionTime;

	
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

	public Date getConnectionTime() {
		return beginConnectionTime;
	}

	public DateTime getConnectionDateTime() {
		return DateTimeConverter.getDateTime(this.beginConnectionTime);	
	}
	
	public void setConnectionTime(Date connectionTime) {
		this.beginConnectionTime = connectionTime;
	}

	public Date getEndConnectionTime() {
		return endConnectionTime;
	}

	public DateTime getEndConnectionDateTime() {
		return DateTimeConverter.getDateTime(this.endConnectionTime);
	}
	
	public void setEndConnectionTime(Date endConnectionTime) {
		this.endConnectionTime = endConnectionTime;
	}
	
	@Override
	public int hashCode() {
		long beginConnectionTime = this.beginConnectionTime.getTime();
		long endConnectionTime = this.endConnectionTime.getTime();
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
