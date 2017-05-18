package com.app.hos.service.managers.device;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import static java.util.concurrent.TimeUnit.SECONDS;
import com.app.hos.persistance.models.Device;
import com.app.hos.share.command.result.DeviceStatus;

@Component
public class DeviceWebSocket {

	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	private DeviceManager deviceManager;
	
	private ScheduledExecutorService scheduler;
	private Future<?> threadHandler;
	
	public DeviceWebSocket() {
		this.scheduler  = Executors.newScheduledThreadPool(1);
		this.threadHandler = scheduler.scheduleAtFixedRate(sendDevicesStatuses, 5, 5, SECONDS);
	}
	
	private  Runnable sendDevicesStatuses = new Runnable() {
        public void run() {
        	Map<Device,DeviceStatus> deviceStatuses = deviceManager.getDeviceStatuses();
        	template.convertAndSend("/topic/device-info", deviceStatuses);
        }
	};

}
