package com.app.hos.service.websocket.command;

import com.app.hos.service.SimpleFactory;
import com.app.hos.service.websocket.command.builder_v2.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.builder_v2.concretebuilders.GetAllDevicesWebCommandBuilder;

// TODO:
// remove JSON_EXCEPTION and EXECUTION_EXCEPTION, its only reason why command cannot be executed
// use 'status' field by set true/false and set message with exception type if occure
// AD1: JSON_EXCEPTION should stay, for example when cannot convert json object (it is broken)
public enum WebCommandType implements SimpleFactory<AbstractWebCommandBuilder>{
	
	RESPONSE(() -> null),
	JSON_EXCEPTION(() -> null),
	GET_ALL_DEVICES(() -> new GetAllDevicesWebCommandBuilder());
	//GET_DEVICE_STATUSES(() -> null),  -> REST API
	//REMOVE_DEVICE(() -> null), -> REST API
	//BLOCK_DEVICE(() -> null), -> REST API
	//DISCONNECT_DEVICE (() -> null); -> REST API

	private SimpleFactory<AbstractWebCommandBuilder> factory;
	
	private WebCommandType(SimpleFactory<AbstractWebCommandBuilder> factory) {
		this.factory = factory;
	}
	
	@Override
	public AbstractWebCommandBuilder create() {
		return factory.create();
	}
	
}
