package com.app.hos.tests.units.utils.json;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.Utils;
import com.app.hos.utils.json.JsonConverter;

public class DevieceStatusesTest {
	
	private static Map<Device,DeviceStatus> deviceStatuses = new HashMap<Device,DeviceStatus>();

	@BeforeClass
	public static void prepareDataTest () {

		Connection connection = new Connection.Builder().connectionId("192.168.0.21:23451-09:oa9:sd1")
														.hostname("localhost")
														.ip("192.168.0.21")
														.remotePort(23451)
														.connectionTime(new DateTime())
														.build();

		Device device = new Device("Device1", "98547kjyy1", new DeviceTypeEntity(DeviceType.PHONE));
		
		connection.setId(1);
		device.setId(1);
		
		connection.setDevice(device);
		device.setConnection(connection);
		
		List<DeviceStatus> statuses = new LinkedList<DeviceStatus>();
		DeviceStatus testStatus = new DeviceStatus(new DateTime(),Utils.generateRandomDouble(),Utils.generateRandomDouble());
		statuses.add(testStatus);
		device.setDeviceStatuses(statuses);
		
		deviceStatuses.put(device, testStatus);
	
		connection = new Connection.Builder().connectionId("192.168.0.22:23452-09:oa9:sd1")
												.hostname("localhost2")
												.ip("192.168.0.22")
												.remotePort(23452)
												.connectionTime(new DateTime())
												.build();

		device = new Device("Device2", "98547kjyy12",new DeviceTypeEntity(DeviceType.PHONE));
		
		connection.setId(2);
		device.setId(2);
		
		connection.setDevice(device);
		device.setConnection(connection);
		
		statuses = new LinkedList<DeviceStatus>();
		statuses.add(testStatus);
		device.setDeviceStatuses(statuses);
		
		deviceStatuses.put(device, testStatus);
	}
	
	@Test
	public void setEmptyMapAndVerifyJsonRepresentation() {
		Map<Device,DeviceStatus> emptyMap = new HashMap<Device,DeviceStatus>();
		String json = JsonConverter.getJson(emptyMap);
		Assert.assertEquals("[]", json);
	}
	
	@Test
	public void setNullAsKeyAndVerifyJsonRepresentation() {
		Map<Device,DeviceStatus> nullkey = new HashMap<Device,DeviceStatus>();
		for (Map.Entry<Device, DeviceStatus> statuses : deviceStatuses.entrySet()) {
		    nullkey.put(statuses.getKey(), null);
		    break;
		}
	//	String json = JsonConverter.getJson(nullkey);
	}
	
	@Test
	public void setNullAsOneValueOfStatusAndVerifyJsonRepresentation() {

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
		expectedJson.append("\",\"connection\":{\"connectionId\":\"" + device.getConnection().getConnectionId());
		expectedJson.append("\",\"hostname\":\"" + device.getConnection().getHostname());
		expectedJson.append("\",\"ip\":\"" + device.getConnection().getIp());
		expectedJson.append("\",\"remotePort\":" + device.getConnection().getRemotePort());
		expectedJson.append(",\"connectionTime\":{\"year\":" + device.getConnection().getConnectionTime().getYear());
		expectedJson.append(",\"month\":" + device.getConnection().getConnectionTime().getMonth());
		expectedJson.append(",\"day\":" + device.getConnection().getConnectionTime().getDay());
		expectedJson.append(",\"hour\":" + device.getConnection().getConnectionTime().getHour());
		expectedJson.append(",\"minutes\":" + device.getConnection().getConnectionTime().getMinutes());
		expectedJson.append(",\"seconds\":" + device.getConnection().getConnectionTime().getSeconds()+"}");
		expectedJson.append(",\"endConnectionTime\":null,\"new\":false}");
		expectedJson.append(",\"deviceStatuses\":[{\"time\":{\"year\":" + status.getTime().getYear());
		expectedJson.append(",\"month\":" + status.getTime().getMonth());
		expectedJson.append(",\"day\":" + status.getTime().getDay());
		expectedJson.append(",\"hour\":" + status.getTime().getHour());
		expectedJson.append(",\"minutes\":" + status.getTime().getMinutes());
		expectedJson.append(",\"seconds\":" + status.getTime().getSeconds()+"}");
		expectedJson.append(",\"new\":true");
		expectedJson.append(",\"ram\":"+status.getRamUsage());
		expectedJson.append(",\"cpu\":"+status.getCpuUsage());
		//expectedJson.append(",\"deviceStatuses\":[]}");
		expectedJson.append("}]},\"devicestatus\":{\"time\":{\"year\":" + status.getTime().getYear());
		expectedJson.append(",\"month\":" + status.getTime().getMonth());
		expectedJson.append(",\"day\":" + status.getTime().getDay());
		expectedJson.append(",\"hour\":" + status.getTime().getHour());
		expectedJson.append(",\"minutes\":" + status.getTime().getMinutes());
		expectedJson.append(",\"seconds\":" + status.getTime().getSeconds()+"}");
		expectedJson.append(",\"ramUsage\":" + status.getRamUsage());
		expectedJson.append(",\"cpuUsage\":" + status.getCpuUsage() +"}}");
		return expectedJson.toString();
	}
}
