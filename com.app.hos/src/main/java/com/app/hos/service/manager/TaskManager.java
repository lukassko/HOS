package com.app.hos.service.manager;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.hos.persistance.models.Device;
import com.app.hos.service.integration.server.Server;

@Component
public class TaskManager {

	private Server server;
	
	@Autowired
	public TaskManager(Server server){
		this.server = server;
		createTestDevices(server);
	}
	
	private void createTestDevices(Server server) {
		DateTime t1 = new DateTime();
		Device d1 = new Device("122", "Device 1", "192.158.0.45", "1233",t1);
		server.createTestNewDevice("1", d1);
		
		Device d2 = new Device("12331", "Device 2", "192.158.0.35", "3233",t1);
		server.createTestNewDevice("2", d2);
		
		Device d3 = new Device("1982", "Device 3", "192.158.0.55", "12344",t1);
		server.createTestNewDevice("3", d3);
	}
	
	public Set<Device> getConnectedDevices () {
		return server.getDevicesList();
	}
}
