package com.app.hos.tests.persistance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.tests.config.PersistanceConfig;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class})
@ActiveProfiles("test-sqlite")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSaveUpdateDevices {

	private static List<Device> devicesList = new LinkedList<Device>();
	
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
		
    	devicesList.add(device1);
    	devicesList.add(device2);
    	devicesList.add(device3);
    }
    
    @Test
    @Rollback(true)
    public void stage1_saveOneDeviceTest() {
    	Device device = devicesList.get(0);
    	deviceRepository.save(device);
    	Collection<Device> devices = deviceRepository.findAll();
    	List<Device> deviceList = new LinkedList<Device>(devices);
    	Assert.assertFalse(deviceList.isEmpty());
    	Assert.assertEquals(device.getName(),deviceList.get(0).getName());
    }

    @Test
    @Rollback(false)
    public void stage2_saveDevicesTest() {
    	deviceRepository.save(devicesList.get(0));
    	deviceRepository.save(devicesList.get(1));
    	deviceRepository.save(devicesList.get(2));
    	Collection<Device> devices = deviceRepository.findAll();
    	List<Device> deviceList = new LinkedList<Device>(devices);
    	Assert.assertEquals(3,deviceList.size());
    }
    
    @Test
    public void stage3_findDeviceByIdTest() {
    	Device device1 = deviceRepository.findDeviceById(1);
    	Device device2 = deviceRepository.findDeviceById(10);
    	Assert.assertNotNull(device1);
    	Assert.assertNull(device2);
    }
    
    @Test
    public void stage4_findDeviceBySerialTest() {
    	Device device = deviceRepository.findDeviceBySerialNumber("98547kjyy2");
    	Assert.assertNotNull(device);
    }
    
    @Test(expected=NoResultException.class)
    public void stage5_findDeviceBySerialShouldThrowExceptionTest() {
    	deviceRepository.findDeviceBySerialNumber("98547kffff");
    }
    
    //@Test
    //@Rollback(false)
    public void stage6_updateDeviceWithNewConnectionTest() {
    	Connection connection4 = new Connection("192.168.0.24:23454-09:oa9:sd4", 
    			"localhost4", "192.168.0.24", 23454, new DateTime());
    
    	Device device = deviceRepository.findDeviceById(1);
    	device.getConnection();
    	device.setConnection(connection4);
    	deviceRepository.save(device);
    	Assert.assertNotNull(connection4);
    }
}
