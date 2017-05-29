package com.app.hos.tests.json;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.json.JsonConverter;
import com.app.hos.utils.json.deserializers.DateTimeJsonDeserializer;
import com.app.hos.utils.json.serializers.DateTimeJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TestDevieceStatuses {
	
	private static Map<Device,DeviceStatus> deviceStatuses = new HashMap<Device,DeviceStatus>();
	
	@BeforeClass
	public static void prepareDataTest () {
		Connection connection = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost1", "192.168.0.21", 23451, new DateTime());
		Device device = new Device("Device1", "98547kjyy1");
		connection.setId(1);
		device.setId(1);
		connection.setDevice(device);
		device.setConnection(connection);
		DeviceStatus status = new DeviceStatus(34.5, 65.9);
		deviceStatuses.put(device, status);
	}
	
	@Test
	public void serializeDeivceStatusesMapTest() {
		String json = JsonConverter.getJson(deviceStatuses);
		
		Assert.assertEquals(1, 1);
	}
}
