package com.app.hos.persistance.models.device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.connection.Connection;

@Table(name = "devices")
@Entity
public class Device extends BaseEntity  {
	
	@NotEmpty
	@Column(nullable = false)
	private String name;
	
	@NotEmpty
	@Column(nullable = false)
	private String serial;
	
	@OneToOne(mappedBy = "device",cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Connection connection;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="device_id")
	private List<DeviceStatus> deviceStatuses = new ArrayList<DeviceStatus>();

	public Device(){}
	
	public Device(String name, String serial) {
		this.name = name;
		this.serial = serial;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		if (this.connection == null) {
			this.connection = connection;
		} else {
			this.connection.setDevice(this);
			this.connection.setConnectionId(connection.getConnectionId());
			this.connection.setHostname(connection.getHostname());
			this.connection.setIp(connection.getIp());
			this.connection.setRemotePort(connection.getRemotePort());
			this.connection.setConnectionTime(connection.getConnectionTime());
			this.connection.setEndConnectionTime(connection.getEndConnectionTime());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public List<DeviceStatus> getDeviceStatuses() {
		Collections.sort(deviceStatuses);
		return deviceStatuses;
	}

	public void setDeviceStatuses(List<DeviceStatus> deviceStatuses) {
		this.deviceStatuses = deviceStatuses;
	}
	
	public DeviceStatus getLastStatus() {
		List<DeviceStatus> statuses = getDeviceStatuses();
		int size = statuses.size();
		if (size == 0) {
			return null;
		} else {
			return statuses.get(size-1);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connection == null) ? 0 : connection.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serial == null) ? 0 : serial.hashCode());
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
		if (connection == null) {
			if (other.connection != null)
				return false;
		} else if (!connection.equals(other.connection))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serial == null) {
			if (other.serial != null)
				return false;
		} else if (!serial.equals(other.serial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Device [name=" + name + ", serial=" + serial + ", connection=" + connection + "]";
	}


}
