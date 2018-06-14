package com.app.hos.tests.integrations.persistance.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.config.init.PostContextLoader;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.ConnectionRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerFactory;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;
import com.app.hos.share.command.builder_v2.CommandFactory;
import com.app.hos.share.command.future.FutureCommandFactory;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.ApplicationContextProvider;

import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = {
				ApplicationContextProvider.class,
				MysqlPersistanceConfig.class,
				PostContextLoader.class,
				CommandFactory.class,
				HOSExceptionHandlerFactory.class,
				FutureWebCommandFactory.class,
				FutureCommandFactory.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class ConnectionRepositoryIT {

	@Autowired
    private ConnectionRepository connectionRepository;
	
	// repository need to save connection with device
		// connection without device cannot exists
	@Autowired
    private DeviceRepository deviceRepository;
	
	@Test
	public void test00_findMethodCalledWithIncorrectConnectionIdShouldReturnNull() {
		// given
		// when
		Connection connection = connectionRepository.find("invalid_connection_id");
			
		// then
		Assert.assertNull(connection);
	}
	
	@Test
	public void test10_afterSaveDeiviceWithConnectionRepositoryShouldFindConnectionBySerialId () {
		// given
		String connectionId = "connection_id_10";
		DeviceTypeEntity typeEntity = deviceRepository.findType(DeviceType.PHONE);
		Device device = new Device("device_10", "serial_10", typeEntity);
		Connection connection = new Connection.Builder()
												.connectionId(connectionId)
												.connectionTime(new DateTime())
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.build();
		device.setConnection(connection);
				
		// when
		deviceRepository.save(device);
		Connection foundConnection = connectionRepository.find(connectionId);
		
		// then
		Assert.assertEquals(connection, foundConnection);
	}
	
	@Test(expected=PersistenceException.class)
	public void test20_saveHistoryConnectionWithInvalidConnectionTimeShouldThrowException () {
		// given
		Device device = deviceRepository.find("serial_10"); // device from previous test
		Connection connection = new Connection.Builder()
												.connectionId("connection_id_20")
												.connectionTime(null)
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.endConnectionTime(new DateTime())
												.build();
				
		// when
		HistoryConnection historyConnection = HistoryConnection.getInstance(connection);
		connectionRepository.save(historyConnection);
		
		// then
			// expected exception
	}
	
	@Test
	public void test30_saveHistoryConnectionWithValidFieldsShouldInsertEntityToDb () {
		// given
		long timestamp = 1528996492;
		DateTime from = new DateTime(timestamp);
		DateTime to = new DateTime(timestamp + 100);
		Device device = deviceRepository.find("serial_10"); // device from previous test
		Connection connection = new Connection.Builder()
												.connectionId("connection_id_20")
												.connectionTime(from)
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.endConnectionTime(to)
												.build();
		HistoryConnection historyConnection = HistoryConnection.getInstance(connection);
		
		// when
		connectionRepository.save(historyConnection);
		List<HistoryConnection> foundForDevice = connectionRepository.findHistoryConnectionsForDevice(device.getId());
		List<HistoryConnection> foundForPeriod = connectionRepository.findHistoryConnectionsForPeriod(from, to);
		List<HistoryConnection> noSamplesInPeriod = connectionRepository.findHistoryConnectionsForPeriod(new DateTime(timestamp - 100), new DateTime(timestamp - 10));
		
		// then
		Assert.assertEquals(1, foundForDevice.size());
		Assert.assertEquals(1, foundForPeriod.size());
	    Assert.assertEquals(0, noSamplesInPeriod.size());
	}
	
	
}
