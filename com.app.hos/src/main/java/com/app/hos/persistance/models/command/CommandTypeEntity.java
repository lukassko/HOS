package com.app.hos.persistance.models.command;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.service.command.type.CommandType;

@Table(name = "commands_type")
@Entity
public class CommandTypeEntity extends BaseEntity  {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private CommandType type;

	@ManyToMany(mappedBy = "commands", 
				fetch = FetchType.LAZY,
				cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	private List<DeviceTypeEntity> devices = new LinkedList<>();
	
	public CommandTypeEntity() {}
	
	public CommandTypeEntity(CommandType type) {
		this.type = type;
	}
	
	public CommandType getType() {
		return type;
	}

	public void setType(CommandType type) {
		this.type = type;
	}

	public List<DeviceTypeEntity> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceTypeEntity> devices) {
		this.devices = devices;
	}

	public void addDeviceType(DeviceTypeEntity deviceTypeEntity) {
		this.getDevices().add(deviceTypeEntity);
	}
}
