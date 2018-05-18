package com.app.hos.tests.units.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.ReflectionUtils;

public class DeviceStatusTest {

	
	@Test
    public void testEqual() {
		DateTime statusTime = new DateTime(1505174400l);
		DeviceStatus status1 = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		
		statusTime = new DateTime(1505174400l);
		DeviceStatus status2 = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());

		Assert.assertTrue("expected to be equal", status1.compareTo(status2)==0);
    }

    @Test
    public void testGreaterThan() {
    	DateTime statusTime = new DateTime(1505001600);
		DeviceStatus status1 = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		
		statusTime = new DateTime(1505174400l);
		DeviceStatus status2 = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());

		Assert.assertTrue("expected to be greater than", status2.compareTo(status1)>=1);
	}

	@Test
	public void testLessThan() {
		DateTime statusTime = new DateTime(1505001600);
		DeviceStatus status1 = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		
		statusTime = new DateTime(1505174400l);
		DeviceStatus status2 = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());

		Assert.assertTrue("expected to be less than", status1.compareTo(status2)<=-1);
	}
	    
	@Test
	public void testSorting() {
		List<DeviceStatus> statuses = new LinkedList<DeviceStatus>();
		
		DateTime statusTime = new DateTime(1505001600);
		DeviceStatus status = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		statuses.add(status);
		
		statusTime = new DateTime(1505088000);
		status = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		statuses.add(status);

		statusTime = new DateTime(1505174400);
		status = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		statuses.add(status);
		
		statusTime = new DateTime(1504915200);
		status = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		statuses.add(status);

		statusTime = new DateTime(1505131200);
		status = new DeviceStatus(statusTime, ReflectionUtils.generateRandomDouble(), ReflectionUtils.generateRandomDouble());
		statuses.add(status);
		
		Collections.sort(statuses);
		
		status = statuses.get(statuses.size() - 1);
		
		Assert.assertEquals(new DateTime(1505174400), status.getTime());
		Assert.assertTrue(1505174400l == status.getTime().getTimestamp().longValue());
	}
	
	@Test
	public void testWithSetCollections() {
		long timestamp = 1505174400l;
		
		DeviceStatus status1 = new DeviceStatus(new DateTime(1505175400l), 13.38, 36.43); // the biggest
		DeviceStatus status2 = new DeviceStatus(new DateTime(timestamp), 23.31, 43.12);
		DeviceStatus status3 = new DeviceStatus(new DateTime(timestamp), 35.32, 86.23);
		DeviceStatus status4 = new DeviceStatus(new DateTime(timestamp), 13.38, 36.43);
		DeviceStatus status5 = new DeviceStatus(new DateTime(1505174100l), 13.38, 36.43); // the smallest
		

		List<DeviceStatus> sortedStatus = new LinkedList<DeviceStatus>();
		sortedStatus.add(status1);
		sortedStatus.add(status2);
		sortedStatus.add(status3);
		sortedStatus.add(status4);
		sortedStatus.add(status5);
		
		Collections.sort(sortedStatus);
		
		Assert.assertEquals(5,sortedStatus.size());
		Assert.assertEquals(status1,sortedStatus.get(4));
		Assert.assertEquals(status5,sortedStatus.get(0));
	}

}
