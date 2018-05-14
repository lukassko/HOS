package com.app.hos.persistance.models.device;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.share.command.type.DeviceType;

@Table(name = "device_type")
@Entity
public class DeviceTypeEntity extends BaseEntity  {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private DeviceType type;
	
	@ManyToMany(fetch = FetchType.LAZY,
				cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
		name = "device_type_command_type", 
		joinColumns = { @JoinColumn(name = "device_type_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "command_type_id") }
	)
	private Set<CommandTypeEntity> commands = new HashSet<CommandTypeEntity>();

	public DeviceTypeEntity() {}
	
	public DeviceTypeEntity (DeviceType type) {
		this.type = type;
	}
	
	public DeviceType getType() {
		return type;
	}

	public void setType(DeviceType type) {
		this.type = type;
	}

	public Set<CommandTypeEntity> getCommands() {
		return commands;
	}

	public void setCommands(Set<CommandTypeEntity> commands) {
		this.commands = commands;
	}

	public void addCommandType(CommandTypeEntity deviceTypeEntity) {
		this.getCommands().add(deviceTypeEntity);
	}
}
