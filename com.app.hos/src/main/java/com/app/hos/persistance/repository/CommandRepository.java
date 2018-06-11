package com.app.hos.persistance.repository;

import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.share.command.type.CommandType;

public interface CommandRepository {
	
	public void save(CommandTypeEntity type);

	public CommandTypeEntity find(int id);
	
	public CommandTypeEntity find(CommandType type);
	
}
