package com.app.hos.persistance.models.connection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.utils.converters.DateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "connections")
@Entity
public class Connection extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(name="connection_id")
	private String connectionId;
	
	private String hostname;
	
	@NotEmpty
	private String ip;
	
	@NotNull
	@Column(name="remote_port")
	private Integer remotePort;

	@NotNull
	@Column(name="connection_time")
	//@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
	private DateTime connectionTime;

	@Column(name="end_connection_time")
	//@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
	private DateTime endConnectionTime;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="device_id")
	private Device device;
	
	public Connection() {}
	
	//use only this constructor, rest to delete!
	public Connection(Builder builder) {
		this.connectionId = builder.connectionId;
		this.hostname = builder.hostname;
		this.ip = builder.ip;
		this.remotePort = builder.remotePort;
		//this.connectionTime = DateTimeConverter.getDate(builder.connectionTime);
		//this.endConnectionTime = DateTimeConverter.getDate(builder.endConnectionTime);
		this.connectionTime = builder.connectionTime;
		this.endConnectionTime = builder.endConnectionTime;
		this.device = builder.device;
	}
	
	public Connection(String connectionId, String hostname, String ip, int remotePort, Date connectionTime) {
		this.connectionId = connectionId;
		this.hostname = hostname;
		this.ip = ip;
		this.remotePort = remotePort;
		//this.connectionTime = connectionTime;
	}
	
	public Connection(String connectionId, String hostname, String ip, int remotePort, DateTime connectionTime) {
		this.connectionId = connectionId;
		this.hostname = hostname;
		this.ip = ip;
		this.remotePort = remotePort;
		//this.connectionTime = DateTimeConverter.getDate(connectionTime);
		//this.connectionTime = connectionTime;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
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

	public DateTime getConnectionTime() {
		//return DateTimeConverter.getDateTime(connectionTime);
		return this.connectionTime;
	}

	public void setConnectionTime(DateTime connectionTime) {
		//this.connectionTime = DateTimeConverter.getDate(connectionTime);
		this.connectionTime = connectionTime;
	}

	public DateTime getEndConnectionTime() {
		//return DateTimeConverter.getDateTime(endConnectionTime);
		return this.endConnectionTime;
	}

	public void setEndConnectionTime(DateTime endConnectionTime) {
		//this.endConnectionTime =  DateTimeConverter.getDate(endConnectionTime);
		this.endConnectionTime =  endConnectionTime;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connectionId == null) ? 0 : connectionId.hashCode());
		result = prime * result + ((connectionTime == null) ? 0 : connectionTime.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
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
		Connection other = (Connection) obj;
		if (connectionId == null) {
			if (other.connectionId != null)
				return false;
		} else if (!connectionId.equals(other.connectionId))
			return false;
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
		if (remotePort == 0) {
			if (other.remotePort != 0)
				return false;
		} else if (!remotePort.equals(other.remotePort))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "Connection [connectionId=" + connectionId + ", hostname=" + hostname + ", ip=" + ip + ", remotePort="
				+ remotePort + ", connectionTime=" + connectionTime;
	}	
	
	public static class Builder {

		private String connectionId;
		
		private String hostname;

		private String ip;

		private Integer remotePort;

		private DateTime connectionTime;

		private DateTime endConnectionTime;
		
		private Device device;
		
		public Builder connectionId(String connectionId) {
			this.connectionId = connectionId;
			return this;
		}
		
		public Builder hostname(String hostname) {
			this.hostname = hostname;
			return this;
		}
		
		public Builder ip(String ip) {
			this.ip = ip;
			return this;
		}
		
		public Builder remotePort(Integer remotePort) {
			this.remotePort = remotePort;
			return this;
		}
		
		public Builder connectionTime(DateTime connectionTime) {
			this.connectionTime = connectionTime;
			return this;
		}
		
		public Builder endConnectionTime(DateTime endConnectionTime) {
			this.endConnectionTime = endConnectionTime;
			return this;
		}
		
		public Builder device(Device device) {
			this.device = device;
			return this;
		}
		
		public Connection build() {
			return new Connection(this);
		}
	}
}
