package com.app.hos.service.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;

@Service
public class WebCommandManager {

	@Autowired
	private WebCommandFactory webCommandFactory;

	public WebCommand executeCommand(WebCommand command) {
		WebCommandType commandType = command.getType();
		if (commandType == WebCommandType.GET_ALL_DEVICES) {
			return webCommandFactory.getCommand(WebCommandType.GET_ALL_DEVICES);
		}
		return null;
	}
}

//Map<Device,DeviceStatus> deviceStatuses = deviceManager.getDeviceStatuses();
//sendJsonOverWebsocket(JsonConverter.getJson(deviceStatuses));

//Map<Device, DeviceStatus> devicesStatuses = deviceManager.getDeviceStatuses();
//String jsonMap = JsonConverter.getJson(devicesStatuses);
//sendJsonOverWebsocket(jsonMap);