package com.app.hos.tests.integrations.multithreading.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.share.command.type.DeviceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration-test")
@ContextConfiguration(classes = {MysqlPersistanceConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceRepositoryMultithreadIT {

	private static int threadCount = 3;
	private static BlockingQueue<Device> queue = new ArrayBlockingQueue<Device>(3);
	
	@Autowired
    private DeviceRepository deviceRepository;

	
	@Before
    public void beforeClass() {
		Connection connection1 = new Connection("192.168.0.21:23451-09:oa9:sd1", 
    			"localhost1", "192.168.0.21", 23451, new DateTime());
		Device device1 = new Device("Device1", "98547kjyy1",new DeviceTypeEntity(DeviceType.PHONE));
		
		connection1.setDevice(device1);
		device1.setConnection(connection1);
		
		Connection connection2 = new Connection("192.168.0.22:23452-09:oa9:sd2", 
    			"localhost2", "192.168.0.22", 23452, new DateTime());
		
		Device device2 = new Device("Device2", "98547kjyy2",new DeviceTypeEntity(DeviceType.PHONE));
		
		connection2.setDevice(device2);
		device2.setConnection(connection2);
		
		Connection connection3 = new Connection("192.168.0.23:23453-09:oa9:sd3", 
    			"localhost3", "192.168.0.23", 23453, new DateTime());
		Device device3 = new Device("Device3", "98547kjyy3",new DeviceTypeEntity(DeviceType.PHONE));
    	
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
    
    @Test
    public void stage2_editAndSaveMultiDeviceTest() {
    	Collection<Device> devices = deviceRepository.findAll();
    	List<Device> deviceList = new LinkedList<Device>(devices);
    	Assert.assertEquals(threadCount,deviceList.size());
    	ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    	List<Future<Void>> futures = new ArrayList<Future<Void>>();
    	List<Exception> exceptions = new ArrayList<Exception>();
    	final SyncDevices syncDevices = new SyncDevices(deviceList);
    	try {
	        final CountDownLatch allExecutorThreadsReady = new CountDownLatch(threadCount);
	        final CountDownLatch afterInitBlocker = new CountDownLatch(1);
	        final CountDownLatch allDone = new CountDownLatch(threadCount);
	    	for (int x = 0; x < threadCount; x++) {
	    		Callable<Void> callable = new Callable<Void>() {
	
					public Void call() throws Exception {
						allExecutorThreadsReady.countDown();
						afterInitBlocker.await();
						
						// start thread action there!
						
						String serial = syncDevices.getDevice().getSerial();
						String newName = Thread.currentThread().getName();
						deviceRepository.updateDeviceNameBySerialNo(serial,newName);
						syncDevices.setDeviceName(serial,newName);
						
						// end thread action there!
						
						allDone.countDown();
						return null;
					}
				};
				Future<Void> submit = executorService.submit(callable);
	            futures.add(submit);
	    	}

	    	afterInitBlocker.countDown();
	    	for (Future<Void> future : futures) {
	    		try {
	                future.get();
	            } catch (Exception e) {
	                exceptions.add(e);
	                e.printStackTrace();
	            }
	    	} 	
			allDone.await();

			// test if threads done their job!
			Device device = deviceRepository.find("98547kjyy1");
	    	Assert.assertEquals(syncDevices.getDeviceNameForSerialNumber("98547kjyy1"),device.getName());
	    	device = deviceRepository.find("98547kjyy2");
	    	Assert.assertEquals(syncDevices.getDeviceNameForSerialNumber("98547kjyy2"),device.getName());
	    	device = deviceRepository.find("98547kjyy3");
	    	Assert.assertEquals(syncDevices.getDeviceNameForSerialNumber("98547kjyy3"),device.getName());
    	} catch (Exception e) { 
    		 exceptions.add(e);
    		 e.printStackTrace();
    	} finally {
	    	executorService.shutdown();
    	}
    	Assert.assertTrue(exceptions.isEmpty());
    }
    
    private class SyncDevices {

    	private List<Device> deviceList;
    	private Map<String,String> deaviceNameMap = new HashMap<String,String>();
    	private int idx;
    	
    	public SyncDevices (List<Device> devices) {
    		this.deviceList = new LinkedList<Device>(devices);
    	}
    	
    	public synchronized Device getDevice() {
    		Device device = deviceList.get(idx);
    		idx++;
    		return device;
    	}
    	
    	public synchronized void setDeviceName(String serial, String name) {
    		this.deaviceNameMap.put(serial, name);
    	}
    	
    	public String getDeviceNameForSerialNumber(String serial) {
    		return this.deaviceNameMap.get(serial);
    	}
    }
}
