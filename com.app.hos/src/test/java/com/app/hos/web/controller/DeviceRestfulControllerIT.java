package com.app.hos.web.controller;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.api.SystemFacade;
import com.app.hos.web.controller.DeviceRestfulController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.LinkedList;
import java.util.List;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
public class DeviceRestfulControllerIT {
	
	private MockMvc mockMvc;
	
	@Mock
    private SystemFacade systemFacade;
	
	@InjectMocks
	@Autowired
	private DeviceRestfulController controller;
	
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
	public void stage00_printHttpResponse () throws Exception {
		
		this.mockMvc.perform(get("/devices/statuses/{serial}","any_serial")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.param("from", "1505001600")
				.param("to", "1505001000"))
	    		.andDo(print());
	}
	
	@Test
	public void stage10_checkHttpBodyResponse() throws Exception {
		
		this.mockMvc.perform(get("/devices/statuses/{serial}","any_serial")
				.param("from", "1505001600")
				.param("to", "1505001000")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		    	.andExpect(status().isOk())
		    	.andExpect(jsonPath("$").isArray())
		    	.andExpect(jsonPath("$[0].cpu").value(83.45))
		    	.andExpect(jsonPath("$[1].ram").value(0.23));
	}

}
