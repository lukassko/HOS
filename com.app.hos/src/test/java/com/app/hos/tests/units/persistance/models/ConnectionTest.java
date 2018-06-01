package com.app.hos.tests.units.persistance.models;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;

public class ConnectionTest {
	
	@Test
	public void testIfBuilderReturnValidConnectionWithEndConnectionTimeAsNull() {
		//given
		String connectionId = "192.168.0.21:23451-09:oa9:sd1";
		String hostname = "localhost";
		String ip = "192.168.0.21";
		int port = 23451;
		DateTime time = new DateTime();
		
		//when
		Connection connection = new Connection.Builder().connectionId(connectionId)
														.hostname("localhost")
														.ip("192.168.0.21")
														.remotePort(23451)
														.connectionTime(time)
														.build();

		//then
		Assert.assertEquals(connectionId, connection.getConnectionId());
		Assert.assertEquals(hostname, connection.getHostname());
		Assert.assertEquals(ip, connection.getIp());
		Assert.assertEquals(port, connection.getRemotePort());
		Assert.assertEquals(connectionId, connection.getConnectionId());
		Assert.assertEquals(time, connection.getConnectionTime());
		Assert.assertNull(connection.getEndConnectionTime());
	}
	
	
}
