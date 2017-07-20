package com.app.hos.tests.integrations.multithreading;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.AspectConfig;
import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.app.hos.share.utils.DateTime;
import com.app.hos.tests.integrations.config.PersistanceConfig;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class , AspectConfig.class})
@ActiveProfiles("integration-test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceRepositoryMultithreadIT {

	private static int threadCount = 3;
	private static BlockingQueue<Device> queue = new ArrayBlockingQueue<Device>(3);
	
	@Autowired
    private DeviceRepository deviceRepository;

	@BeforeClass
    public static void beforeClass() {
		Connection connection1 = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost1", "192.168.0.21", 23451, new DateTime());
		Device device1 = new Device("Device1", "98547kjyy1");
		
		connection1.setDevice(device1);
		device1.setConnection(connection1);
		
		Connection connection2 = new Connection("192.168.0.22:23452-09:oa9:sd2", 
    			"localhost2", "192.168.0.22", 23452, new DateTime());
		
		Device device2 = new Device("Device2", "98547kjyy2");
		
		connection2.setDevice(device2);
		device2.setConnection(connection2);
		
		Connection connection3 = new Connection("192.168.0.23:23453-09:oa9:sd3", 
    			"localhost3", "192.168.0.23", 23453, new DateTime());
		Device device3 = new Device("Device3", "98547kjyy3");
    	
		connection3.setDevice(device3);
		device3.setConnection(connection3);

		try {
			queue.put(device1);
			queue.put(device2);
			queue.put(device3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
    }
    
    @Test
    public void stage1_saveMultiDeviceTest() {
    	Collection<Device> devices = deviceRepository.findAll();
    	List<Device> deviceList = new LinkedList<Device>(devices);
    	Assert.assertTrue(deviceList.isEmpty());
    	ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    	List<Future<Void>> futures = new ArrayList<Future<Void>>();
    	for (int x = 0; x < threadCount; x++) {
    		Callable<Void> callable = new Callable<Void>() {

				public Void call() throws Exception {
					Device device = queue.take();
					deviceRepository.save(device);
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
                exceptions.add(e);
                e.printStackTrace();
            }
    	}
    	executorService.shutdown();
    	
    	devices = deviceRepository.findAll();
    	deviceList = new LinkedList<Device>(devices);
    	
    	Assert.assertTrue(exceptions.isEmpty());
    	Assert.assertEquals(threadCount, deviceList.size());
    }
    
    
}
