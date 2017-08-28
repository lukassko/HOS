package com.app.hos.service.websocket.command;

import java.util.Map;

import com.app.hos.persistance.models.Device;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.utils.json.JsonConverter;

public class GetAllDevicesWebCommandBuilder extends AbstractWebCommandBuilder {


	@Override
	public void setCommandType() {
		command.setType(WebCommandType.GET_ALL_DEVICES);
	}

	@Override
	public void setMessage() {
		Map<Device, DeviceStatus> devicesStatuses = deviceManager.getDeviceStatuses();
		String jsonMap = JsonConverter.getJson(devicesStatuses);
		command.setMessage(jsonMap);
	}

}
