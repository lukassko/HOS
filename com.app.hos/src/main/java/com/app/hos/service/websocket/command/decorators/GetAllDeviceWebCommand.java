package com.app.hos.service.websocket.command.decorators;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.pojo.WebDevice;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.utils.json.JsonConverter;

public class GetAllDeviceWebCommand extends FutureWebCommandDecorator {
	
	public GetAllDeviceWebCommand(WebCommand command) {
		super(command);
	}
	
	public WebCommand call() throws Exception {
		Map<Device, DeviceStatus> devicesStatuses = deviceManager.getLatestDevicesStatuses();
		
		Map<WebDevice, DeviceStatus> webDevicesStatuses =  new TreeMap<WebDevice, DeviceStatus>(
			new Comparator<WebDevice>() {
				
		        public int compare(WebDevice device1, WebDevice device2) {
		    		int idD1 = device1.getId();
		    		int idD2 = device2.getId();
		    		return (idD1 < idD2) ? -1: (idD1 > idD2) ? 1:0;
		        }
			}
		);

		for (Map.Entry<Device, DeviceStatus> entry : devicesStatuses.entrySet()) {
		   Device device = entry.getKey();
		   DeviceStatus status = entry.getValue();
		   webDevicesStatuses.put(new WebDevice(device), status);
		}
		
		String jsonMap = JsonConverter.getJson(webDevicesStatuses);
		command.setMessage(jsonMap);
		return command;
	}

}
