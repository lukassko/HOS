package com.app.hos.persistance.models.device;

import java.util.LinkedList;
import java.util.List;

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
import com.app.hos.service.command.type.DeviceType;

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
	private List<CommandTypeEntity> commands = new LinkedList<CommandTypeEntity>();

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

	public List<CommandTypeEntity> getCommands() {
		return commands;
	}

	public void setCommands(List<CommandTypeEntity> commands) {
		this.commands = commands;
	}

	public void addCommandType(CommandTypeEntity deviceTypeEntity) {
		this.getCommands().add(deviceTypeEntity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceTypeEntity other = (DeviceTypeEntity) obj;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
