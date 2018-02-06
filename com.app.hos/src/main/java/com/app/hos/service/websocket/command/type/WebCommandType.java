package com.app.hos.service.websocket.command.type;

// TODO:
// remove JSON_EXCEPTION and EXECUTION_EXCEPTION, its only reason why command cannot be executed
// use 'status' field by set true/false and set message with exception type if occure
// AD1: JSON_EXCEPTION should stay, for example when cannot convert json object (it is broken)
public enum WebCommandType {
	JSON_EXCEPTION,
	GET_ALL_DEVICES,
	GET_DEVICE_STATUSES,
	REMOVE_DEVICE,
	BLOCK_DEVICE,
	DISCONNECT_DEVICE
}
