package com.app.hos.persistance.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.app.hos.config.init.PostContextLoader;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.command.builder_v2.CommandFactory;
import com.app.hos.service.command.future.FutureCommandFactory;
import com.app.hos.service.command.type.DeviceType;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerFactory;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;
import com.app.hos.utils.ApplicationContextProvider;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class DeviceRepositoryMultithreadIT {

	protected final Logger log = Logger.getLogger(getClass().getName());
	
	private static int threadCount = 4;
	
	@Autowired
    private DeviceRepository deviceRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;
	    
    @Test
    public void test10_multithreadTryToFindAndEditDeviceIfNotExitsOthrewiseCreateNewDevice() {
    
    	// given
    	ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    	List<Future<Void>> futures = new ArrayList<Future<Void>>();
    	
    	// when
    	final String serial = "serial_10";
    	DeviceTypeEntity type = deviceRepository.findType(DeviceType.PHONE);
		Device device = new Device("device_10", serial, type);
		Connection connection = new Connection.Builder()
												.connectionId("connection_id")
												.connectionTime(new DateTime())
												.hostname("hostname")
												.ip("192.168.0.21")
												.remotePort(1234)
												.device(device)
												.build();
		device.setConnection(connection);
		deviceRepository.save(device);
		
    	for (int x = 0; x < threadCount; x++) {
    		Callable<Void> callable = new Callable<Void>() {
				public Void call() throws Exception {
					
					TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);	
					transactionTemplate.execute(new TransactionCallback<Void>() {
					    @Override
					    public Void doInTransaction(TransactionStatus status) {
					    	Device device = deviceRepository.findBySerial(serial);
					    	if (device != null)
					    		device.setName("thread_" + Thread.currentThread().getName());
							return null;
					    }
					});
					return null;
				}
    
			};
			Future<Void> submit = executorService.submit(callable);
            futures.add(submit);
    	}
    	
    	List<Exception> exceptions = new ArrayList<Exception>();
    	for (Future<Void> future : futures) {
    		try {
                future.get();
            } catch (Exception e) {
            	log.log(Level.SEVERE, e.getMessage() , e);
                exceptions.add(e);
            }
    	}
    	executorService.shutdown();
    	
    	List<Device> devices = deviceRepository.findAll();
    	
        // then
    	Assert.assertTrue(exceptions.isEmpty());
    	Assert.assertEquals(1, devices.size());
    	Device selectedDevice = devices.get(0);
    	Assert.assertTrue(selectedDevice.getName().matches("(.*)thread_(.*)"));
    }    
}
