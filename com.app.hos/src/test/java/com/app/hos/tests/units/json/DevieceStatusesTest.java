package com.app.hos.tests.units.json;

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

public class DevieceStatusesTest {
	
	private static Map<Device,DeviceStatus> deviceStatuses = new HashMap<Device,DeviceStatus>();
	private static Device device;
	private static DeviceStatus status;
	@BeforeClass
	public static void prepareDataTest () {
		Connection connection = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost1", "192.168.0.21", 23451, new DateTime());
		Device testDevice = new Device("Device1", "98547kjyy1");
		connection.setId(1);
		testDevice.setId(1);
		connection.setDevice(testDevice);
		testDevice.setConnection(connection);
		DeviceStatus testStatus = new DeviceStatus(34.5, 65.9);
		deviceStatuses.put(testDevice, testStatus);
		device = testDevice;
		status = testStatus;
		
	}
	
	@Test
	public void serializeDeivceStatusesMapTest() {
		String json = JsonConverter.getJson(deviceStatuses);
		System.out.println(json);
		StringBuilder expectedJson = new StringBuilder();
		expectedJson.append("[{\"device\":{\"name\":" + device.getName());
		expectedJson.append(",\"serial\":" + device.getSerial());
		expectedJson.append(",\"connection\":\"{\"id\"" + device.getConnection().getId());
		expectedJson.append(",\"connectionId\":" + device.getConnection().getConnectionId());
		expectedJson.append(",\"hostname\":" + device.getConnection().getHostname());
		expectedJson.append(",\"ip\":" + device.getConnection().getIp());
		expectedJson.append(",\"remotePort\":" + device.getConnection().getRemotePort() +"}");
		Assert.assertEquals(expectedJson.toString(), json);
	}
}
