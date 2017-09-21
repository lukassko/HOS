package com.app.hos.service.websocket.command;

import java.util.HashMap;
import java.util.Map;

import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.pojo.WebDevice;
import com.app.hos.service.websocket.command.builder.AbstractWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.utils.json.JsonConverter;

public class GetAllDevicesWebCommandBuilder extends AbstractWebCommandBuilder {

	@Override
	public void setCommandType() {
		command.setType(WebCommandType.GET_ALL_DEVICES);
	}

	@Override
	public void setMessage() {
		Map<Device, DeviceStatus> devicesStatuses = deviceManager.getLatestDevicesStatuses();
		Map<WebDevice, DeviceStatus> webDevicesStatuses =  new HashMap<WebDevice, DeviceStatus>();
		for (Map.Entry<Device, DeviceStatus> entry : devicesStatuses.entrySet()) {
		   Device device = entry.getKey();
		   DeviceStatus status = entry.getValue();
		   webDevicesStatuses.put(new WebDevice(device), status);
		}
		String jsonMap = JsonConverter.getJson(webDevicesStatuses);
		command.setMessage(jsonMap);
	}

}
