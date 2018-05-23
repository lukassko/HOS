package com.app.hos.service.websocket.command.decorators;

import java.util.Map;
import java.util.TreeMap;

import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.pojo.WebDevice;
import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.future.FutureWebCommand;
import com.app.hos.utils.json.JsonConverter;

@FutureWebCommand(type = WebCommandType.GET_ALL_DEVICES)
public class GetAllDeviceWebCommand extends FutureWebCommandDecorator {
	
	public GetAllDeviceWebCommand(WebCommand command) {
		super(command);
	}
	
	public WebCommand call() throws Exception {
		Map<Device, DeviceStatus> devicesStatuses = devicesApi.getConnectedDevices();
		
		Map<WebDevice, DeviceStatus> webDevicesStatuses =  new TreeMap<>(
				(WebDevice d1, WebDevice d2) -> {
					int id1 = d1.getId();
		    		int id2 = d2.getId();
		    		return (id1 < id2) ? -1: (id1 > id2) ? 1:0;
				}
			);

		for (Map.Entry<Device, DeviceStatus> entry : devicesStatuses.entrySet()) {
		   webDevicesStatuses.put(new WebDevice(entry.getKey()), entry.getValue());
		}

		command.setStatus(true);
		command.setMessage(JsonConverter.getJson(webDevicesStatuses));
		
		return command;
	}

}
