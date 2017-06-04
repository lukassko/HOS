package com.app.hos.service.websocket;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.UnsupportedEncodingException;

import com.app.hos.persistance.models.Device;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.utils.json.JsonConverter;

@Service
public class DeviceWebSocket {

	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	private DeviceManager deviceManager;
	
	private ScheduledExecutorService scheduler;
	private Future<?> threadHandler;
	
	private final String destination = "/topic/device-info";
	
	public DeviceWebSocket() {
		this.scheduler  = Executors.newScheduledThreadPool(1);
		this.threadHandler = scheduler.scheduleAtFixedRate(sendDevicesStatuses, 5, 5, SECONDS);
	}

	private Runnable sendDevicesStatuses = new Runnable() {
        public void run() {
        	Map<Device,DeviceStatus> deviceStatuses = deviceManager.getDeviceStatuses();
        	sendJsonOverWebsocket(JsonConverter.getJson(deviceStatuses));
        }
	};

	public void sendObjectOverWebsocket(Object object) {
		template.convertAndSend(destination, object);
	}
	
	public synchronized void sendObjectOverWebsocket(String destination,Object object) {
		template.convertAndSend(destination, object);
	}
	
	public void sendJsonOverWebsocket(String json) {
		sendJsonOverWebsocket(destination,json);
	}
	
	public synchronized void sendJsonOverWebsocket(String destination,String json) {
		try {
			Message<?> message = MessageBuilder.withPayload(json.getBytes("UTF-8")).build();
			template.send(destination, message);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}
