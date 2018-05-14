package com.app.hos.service.websocket.command.type;

import com.app.hos.service.SimpleFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.future.WebCommandFactory;
import com.app.hos.service.websocket.command.future.concretefactories.GetAllDeviceWebCommandFactory;

// TODO:
// remove JSON_EXCEPTION and EXECUTION_EXCEPTION, its only reason why command cannot be executed
// use 'status' field by set true/false and set message with exception type if occure
// AD1: JSON_EXCEPTION should stay, for example when cannot convert json object (it is broken)
public enum WebCommandType implements SimpleFactory<WebCommandFactory>{
	
	JSON_EXCEPTION(() -> null),
	GET_ALL_DEVICES(() -> new GetAllDeviceWebCommandFactory()),
	GET_DEVICE_STATUSES(() -> null),
	REMOVE_DEVICE(() -> null),
	BLOCK_DEVICE(() -> null),
	DISCONNECT_DEVICE (() -> null);

	private SimpleFactory<WebCommandFactory> factory;
	
	private WebCommandType(SimpleFactory<WebCommandFactory> factory) {
		this.factory = factory;
	}
	
	@Override
	public WebCommandFactory create() {
		return factory.create();
	}
	
}
