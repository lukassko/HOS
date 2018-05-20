package com.app.hos.tests.units.utils;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.utils.Utils;

public class UtilsTest {
	
	@Test
	public void testUtilityClassToGetCpuUsage() {
		double usage = Utils.getCpuUsage();
		Assert.assertTrue(usage <= 100);
		Assert.assertTrue(usage >= 0);
	}
	
	@Test
	public void testUtilityClassToGetRamUsage() {
		double usage = Utils.getRamUsage();
		Assert.assertTrue(usage <= 100);
		Assert.assertTrue(usage >= 0);
	}
}
