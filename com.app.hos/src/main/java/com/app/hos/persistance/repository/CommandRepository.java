package com.app.hos.persistance.repository;

import com.app.hos.persistance.models.command.CommandTypeEntity;

public interface CommandRepository {
	
	public void save(CommandTypeEntity type);

	public CommandTypeEntity find(int id);
}
