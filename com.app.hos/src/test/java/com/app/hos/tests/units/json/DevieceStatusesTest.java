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
	
		connection = new Connection("192.168.0.22:23452-09:oa9:sd1", 
    			"localhost2", "192.168.0.22", 23452, new DateTime());
		testDevice = new Device("Device2", "98547kjyy12");
		connection.setId(2);
		testDevice.setId(2);
		connection.setDevice(testDevice);
		testDevice.setConnection(connection);
		testStatus = new DeviceStatus(11.5, 33.6);
		deviceStatuses.put(testDevice, testStatus);
	}
	
	@Test
	public void serializeDeivceStatusesMapTest() {
		String json = JsonConverter.getJson(deviceStatuses);
		StringBuilder expectedJson = new StringBuilder();
		expectedJson.append("[");
		for(Map.Entry<Device, DeviceStatus> entry : deviceStatuses.entrySet()) {
			Device device = entry.getKey();
			DeviceStatus status = entry.getValue();
			expectedJson.append(getExpetedJsonMap(device,status));
			expectedJson.append(",");
		}
		expectedJson.deleteCharAt(expectedJson.length()-1);
		expectedJson.append("]");
		Assert.assertEquals(expectedJson.toString(),json);
	}
	
	private String getExpetedJsonMap(Device device, DeviceStatus status) {
		StringBuilder expectedJson = new StringBuilder();
		expectedJson.append("{\"device\":{\"name\":\"" + device.getName());
		expectedJson.append("\",\"serial\":\"" + device.getSerial());
		expectedJson.append("\",\"connection\":{\"id\":" + device.getConnection().getId());
		expectedJson.append(",\"connectionId\":\"" + device.getConnection().getConnectionId());
		expectedJson.append("\",\"hostname\":\"" + device.getConnection().getHostname());
		expectedJson.append("\",\"ip\":\"" + device.getConnection().getIp());
		expectedJson.append("\",\"remotePort\":" + device.getConnection().getRemotePort());
		expectedJson.append(",\"connectionTime\":{\"year\":" + device.getConnection().getConnectionTime().getYear());
		expectedJson.append(",\"month\":" + device.getConnection().getConnectionTime().getMonth());
		expectedJson.append(",\"day\":" + device.getConnection().getConnectionTime().getDay());
		expectedJson.append(",\"hour\":" + device.getConnection().getConnectionTime().getHour());
		expectedJson.append(",\"minutes\":" + device.getConnection().getConnectionTime().getMinutes());
		expectedJson.append(",\"seconds\":" + device.getConnection().getConnectionTime().getSeconds());
		expectedJson.append(",\"millis\":" + device.getConnection().getConnectionTime().getMillis()+"}");
		expectedJson.append(",\"endConnectionTime\":null,\"new\":false}}");
		expectedJson.append(",\"devicestatus\":{\"time\":{\"year\":" + status.getTime().getYear());
		expectedJson.append(",\"month\":" + status.getTime().getMonth());
		expectedJson.append(",\"day\":" + status.getTime().getDay());
		expectedJson.append(",\"hour\":" + status.getTime().getHour());
		expectedJson.append(",\"minutes\":" + status.getTime().getMinutes());
		expectedJson.append(",\"seconds\":" + status.getTime().getSeconds());
		expectedJson.append(",\"millis\":" + status.getTime().getMillis()+"}");
		expectedJson.append(",\"ramUsage\":" + status.getRamUsage());
		expectedJson.append(",\"cpuUsage\":" + status.getCpuUsage() +"}}");
		return expectedJson.toString();
	}
}
