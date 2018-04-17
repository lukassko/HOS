package com.app.hos.tests.integrations.controllers;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.models.User;
import com.app.hos.security.authentication.HosUserAuthentication;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.security.states.concretestates.AuthenticatedState;
import com.app.hos.web.controller.UserRestfulController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlPersistanceConfig.class, ApplicationContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
public class UserRestfulControllerIT {
	
	private MockMvc mockMvc;
	
	@Autowired
	private UserRestfulController controller;
	
	@Before
    public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@Test
	public void stage00_noAuthenticatorAtributeInSessionShouldReturnEmptyJson () throws Exception {
		
		this.mockMvc.perform(get("/user/getactive")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
	    		.andDo(print())
	    		.andExpect(content().string(""));
	}
	
	@Test
	public void stage10_setAuthenticatorAtributeToSessionAndReturnProperUserName () throws Exception {
		MockHttpSession mockHttpSession = new MockHttpSession(); 
		
		StatesAuthenticator statesAuthenticator = new StatesAuthenticator();
		User user = new User("Lukasz");
		user.setPassword("admin");
		
		HosUserDetails userDetail = new HosUserDetails(user);
		Authentication authentication = new HosUserAuthentication(userDetail);
		statesAuthenticator.setState(new AuthenticatedState(authentication));
		
		statesAuthenticator.setAuthentication(authentication);
		
		mockHttpSession.setAttribute("authenticator", statesAuthenticator);             
		
		this.mockMvc.perform(get("/user/getactive").session(mockHttpSession)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
	    		.andDo(print())
	    		.andExpect(jsonPath("$.name").value("Lukasz"));;
		
	}

}
