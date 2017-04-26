package com.app.hos.service.managers.device;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.app.hos.persistance.models.Device;
import com.app.hos.share.command.result.DeviceStatus;

public class DeviceStatusSenderThread implements Runnable {

	@Autowired
    private SimpMessagingTemplate template;
	
	private DeviceManager deviceManager;
	
	public DeviceStatusSenderThread(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}
	
	public void run() {
		Map<Device,DeviceStatus> deviceStatuses = deviceManager.getDeviceStatuses();
		this.template.convertAndSend("/topic/device-info", deviceStatuses);
	}

}
