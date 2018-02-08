package com.app.hos.tests.integrations.controllers;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.controller.RestfulController;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.service.SystemFacade;
import com.app.hos.share.utils.DateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.LinkedList;
import java.util.List;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
public class RestfulControllerIT {
	
	private MockMvc mockMvc;
	
	@Mock
    private SystemFacade systemFacade;
	
	@InjectMocks
	@Autowired
	private RestfulController controller;
	
	@Before
    public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
				 
		List<DeviceStatus> statuses = new LinkedList<>();
		statuses.add(new DeviceStatus(new DateTime(),3.89, 83.45));
		statuses.add(new DeviceStatus(new DateTime(),0.23, 13.41));
		statuses.add(new DeviceStatus(new DateTime(),65.22,54.12));
		
		Mockito.when(systemFacade.getDeviceStatuses(Mockito.anyString(), Mockito.any(DateTime.class), Mockito.any(DateTime.class)))
			   .thenReturn(statuses);
    }
	
	@Test
	public void test () throws Exception {
		
		this.mockMvc.perform(get("/devices/statuses/{serial}","any_serial")
				.param("from", "1505001600")
				.param("to", "1505001000"))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$[1].title", "sad"));
	}
		

}
