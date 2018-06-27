package com.app.hos.persistance.models.device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.connection.Connection;

@SuppressWarnings("serial")
@Table(name = "devices")
@Entity
public class Device extends BaseEntity  {
	
	@ManyToOne
	@JoinColumn(name = "device_type_id")
	private DeviceTypeEntity deviceType;

	@NotBlank
	private String name;
	
	@NotBlank
	private String serial;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "connection_id",nullable=false)
	@NotNull
	private Connection connection;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="device_id")
	private List<DeviceStatus> deviceStatuses = new ArrayList<DeviceStatus>();

	public Device(){}
	
	public Device(String name, String serial, DeviceTypeEntity type) {
		this.name = name;
		this.serial = serial;
		this.deviceType = type;
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

	public DeviceTypeEntity getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceTypeEntity type) {
		this.deviceType = type;
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
		if (deviceType == null) {
			if (other.deviceType != null)
				return false;
		} else if (deviceType.getType() != other.deviceType.getType())
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
		return "Device [type=" + deviceType.getType().name() + ", name=" + name + ", serial=" + serial + ", connection=" + connection + "]";
	}

}
