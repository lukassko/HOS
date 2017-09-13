package com.app.hos.tests.units.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.Utils;

public class DeviceStatusTest {

	
	@Test
    public void testEqual() {
		DateTime statusTime = new DateTime(1505174400l);
		DeviceStatus status1 = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		
		statusTime = new DateTime(1505174400l);
		DeviceStatus status2 = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());

		Assert.assertTrue("expected to be equal", status1.compareTo(status2)==0);
    }

    @Test
    public void testGreaterThan() {
    	DateTime statusTime = new DateTime(1505001600);
		DeviceStatus status1 = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		
		statusTime = new DateTime(1505174400l);
		DeviceStatus status2 = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());

		Assert.assertTrue("expected to be greater than", status2.compareTo(status1)>=1);
	}

	@Test
	public void testLessThan() {
		DateTime statusTime = new DateTime(1505001600);
		DeviceStatus status1 = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		
		statusTime = new DateTime(1505174400l);
		DeviceStatus status2 = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());

		Assert.assertTrue("expected to be less than", status1.compareTo(status2)<=-1);
	}
	    
	@Test
	public void testSorting() {
		List<DeviceStatus> statuses = new LinkedList<DeviceStatus>();
		
		DateTime statusTime = new DateTime(1505001600);
		DeviceStatus status = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		statuses.add(status);
		
		statusTime = new DateTime(1505088000);
		status = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		statuses.add(status);

		statusTime = new DateTime(1505174400);
		status = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		statuses.add(status);
		
		statusTime = new DateTime(1504915200);
		status = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		statuses.add(status);

		statusTime = new DateTime(1505131200);
		status = new DeviceStatus(statusTime, Utils.generateRandomDouble(), Utils.generateRandomDouble());
		statuses.add(status);
		
		Collections.sort(statuses);
		
		status = statuses.get(statuses.size() - 1);
		
		Assert.assertEquals(new DateTime(1505174400), status.getTime());
		Assert.assertTrue(1505174400l == status.getTime().getTimestamp().longValue());
	}

}
