package com.app.hos.tests.integrations.persistance.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.ConnectionRepository;
import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.share.command.type.DeviceType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectionRepositoryIT {

	private static List<Connection> connectionList = new LinkedList<Connection>();
	private static List<Device> devicesList = new LinkedList<Device>();
	
	@Autowired
    private ConnectionRepository connectionRepository;
	
	@BeforeClass
    public static void beforeClass() {
		Connection connection1 = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost1", "192.168.0.21", 23451, new DateTime());
		
		Connection connection2 = new Connection("192.168.0.22:23452-09:oa9:sd2", 
    			"localhost2", "192.168.0.22", 23452, new DateTime());
		
		Connection connection3 = new Connection("192.168.0.23:23453-09:oa9:sd3", 
    			"localhost3", "192.168.0.23", 23453, new DateTime());
		
		Device device = new Device("Device1", "98547kjyy1", new DeviceTypeEntity(DeviceType.PHONE));
		device.setId(1);
		
		DateTime endConnectionTime = new DateTime();
		connection1.setEndConnectionTime(endConnectionTime);
		
		endConnectionTime = new DateTime();
		connection2.setEndConnectionTime(endConnectionTime);
		
		endConnectionTime = new DateTime();
		connection3.setEndConnectionTime(endConnectionTime);
		//Device device2 = new Device("Device2", "98547kjyy2");
		//Device device3 = new Device("Device3", "98547kjyy3");
		
		connection1.setDevice(device);
		connection2.setDevice(device);
		connection3.setDevice(device);

		connectionList.add(connection1);
		connectionList.add(connection2);
		connectionList.add(connection3);

		devicesList.add(device);
    }
	
	//@Test(expected=HistoryConnectionException.class)
	public void saveMethodShouldThrowHistoryConnectionExceptionTest () throws HistoryConnectionException {
		Connection connection = connectionList.get(0);
		HistoryConnection historyConnection = HistoryConnection.getInstance(connection);
		//connection.setEndConnectionTime(null);
		connectionRepository.save(historyConnection);
	}
	
	@Transactional
	@Test
	public void saveMethodShouldInsertConnectionToDb () throws HistoryConnectionException {
		Connection connection = connectionList.get(0);
		Device device = devicesList.get(0);
		connectionRepository.save(HistoryConnection.getInstance(connection));
		Collection<HistoryConnection> connections = connectionRepository.findAllHistoryConnectionsByDeviceId(device.getId());
		List<HistoryConnection> connectionsList = new LinkedList<HistoryConnection>(connections);
		Assert.assertEquals(1,connectionsList.size());
	}
	
	@Transactional
	@Test
	public void saveAndSelectMultipleConnectionFromOneDeviceTest () throws HistoryConnectionException {
		Device device = devicesList.get(0);
		Connection connection1 = connectionList.get(0);
		Connection connection2 = connectionList.get(1);
		Connection connection3 = connectionList.get(2);
		connectionRepository.save(HistoryConnection.getInstance(connection1));
		connectionRepository.save(HistoryConnection.getInstance(connection2));
		connectionRepository.save(HistoryConnection.getInstance(connection3));
		Collection<HistoryConnection> connections = connectionRepository.findAllHistoryConnectionsByDeviceId(device.getId());
		List<HistoryConnection> connectionsList = new LinkedList<HistoryConnection>(connections);
		Assert.assertEquals(3,connectionsList.size());
	}
   
	@Transactional
	@Test
	public void addNewConnectionAndCheckIfConnectionAreEquals () {
		
	}
	
}
