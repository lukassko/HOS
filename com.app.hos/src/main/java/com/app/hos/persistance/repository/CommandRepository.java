package com.app.hos.persistance.repository;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.models.device.DeviceTypeEntity;

public interface CommandRepository {
	
	public void save(CommandTypeEntity type);

}
