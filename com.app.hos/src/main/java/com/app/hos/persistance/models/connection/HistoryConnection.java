package com.app.hos.persistance.models.connection;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.service.exceptions.HistoryConnectionException;

// immutable object
@Table(name = "finalised_connections")
@Entity
public class HistoryConnection extends BaseEntity {

	@Column(name="device_id",nullable=false)
	private int deviceId;
	
	@Column(name="ip")
	@NotBlank
	private String ip;

	@Column(name="remote_port",nullable=false)
	private int remotePort;

	@Column(name="begin_connection_time", nullable=false)
	@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
	private DateTime beginConnectionTime;

	@Column(name="end_connection_time", nullable=false)
	@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
	private DateTime endConnectionTime;
	
	public int getDeviceId() {
		return deviceId;
	}

	private void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getIp() {
		return ip;
	}

	private void setIp(String ip) {
		this.ip = ip;
	}

	public int getRemotePort() {
		return remotePort;
	}

	private void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public DateTime getConnectionTime() {
		return beginConnectionTime;
	}

	public DateTime getConnectionDateTime() {
		return this.beginConnectionTime;	
	}
	
	private void setConnectionTime(DateTime connectionTime) {
		this.beginConnectionTime = connectionTime;
	}

	public DateTime getEndConnectionTime() {
		return endConnectionTime;
	}
	
	private void setEndConnectionTime(DateTime endConnectionTime) {
		this.endConnectionTime = endConnectionTime;
	}
	
	// static factory method
	public static HistoryConnection getInstance(Connection connection) throws HistoryConnectionException {
		if (connection.getEndConnectionTime() == null) 
			throw new HistoryConnectionException(connection);

		HistoryConnection historyConnection = new HistoryConnection();
		historyConnection.setConnectionTime(connection.getConnectionTime());
		historyConnection.setEndConnectionTime(connection.getEndConnectionTime());
		historyConnection.setDeviceId(connection.getDevice().getId());
		historyConnection.setIp(connection.getIp());
		historyConnection.setRemotePort(connection.getRemotePort());
		
		return historyConnection;
	}
	
	@Override
	public int hashCode() {
		long beginConnectionTime = this.beginConnectionTime.getTimestamp();
		long endConnectionTime = this.endConnectionTime.getTimestamp();
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
