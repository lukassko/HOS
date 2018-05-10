package com.app.hos.persistance.models.command;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.share.command.type.CommandType;

@Table(name = "commands_type")
@Entity
public class CommandTypeEntity extends BaseEntity  {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private CommandType type;

	@ManyToMany(mappedBy = "commands", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<DeviceTypeEntity> devices = new HashSet<>();
	
	public CommandTypeEntity(CommandType type) {
		this.type = type;
	}
	
	public CommandType getType() {
		return type;
	}

	public void setType(CommandType type) {
		this.type = type;
	}

	public Set<DeviceTypeEntity> getDevices() {
		return devices;
	}

	public void setDevices(Set<DeviceTypeEntity> devices) {
		this.devices = devices;
	}

	public void addDeviceType(DeviceTypeEntity deviceTypeEntity) {
		this.getDevices().add(deviceTypeEntity);
	}
}
