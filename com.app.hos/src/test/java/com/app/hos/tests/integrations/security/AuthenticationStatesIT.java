package com.app.hos.tests.integrations.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.WebSecurityConfig;
import com.app.hos.security.filters.AuthenticationFilter;
import com.app.hos.security.states.AuthenticationState;
import com.app.hos.security.states.StatesAuthenticator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.logging.Logger;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
		classes = {ApplicationContextConfig.class, WebSecurityConfig.class},
		loader = AnnotationConfigWebContextLoader.class,
		initializers = WebSecurityConfig.class
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationStatesIT {
	
	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
    public void initMocks(){

		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(new AuthenticationFilter(), "/*")
				.build();
    }

 
	@Test
	public void stage10_firstNotLoginRequestShouldMakeRedirectAndAddAuthenticatorToSession () throws Exception {

		 MvcResult mvcResult = mockMvc.perform(get("/"))
				 						.andDo(print())
				 						//.andExpect(cookie().exists("JSESSIONID"))
				 						.andExpect(status().is3xxRedirection()).andReturn();
		 
		 MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();

		
		 StatesAuthenticator authenticator = (StatesAuthenticator)session.getAttribute("authenticator");
		 AuthenticationState state = authenticator.getState();
		 
		 Assert.assertNotNull(authenticator);
		 Assert.assertNotNull(state);
	}

	@Test
	public void stage20_firstNotLoginRequestShouldMakeRedirectSecondRequestShouldBeLogginAndExpectOkStatus () throws Exception {

		 MvcResult mvcResult = mockMvc.perform(get("/"))
					.andDo(print())
					.andExpect(status().is3xxRedirection()).andReturn();

		MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
		
		mockMvc.perform(get("/login").session(session))
         								.andDo(print())
         								//.andExpect(cookie().exists("JSESSIONID"))
         								.andExpect(status().isOk());
		 
	}
	
	@Test
	public void stage30_firstRequestForLoginPageShouldReturnProperPageAndAddUnauthenticatedStateToSession () throws Exception {

		 MvcResult mvcResult = mockMvc.perform(get("/"))
					.andDo(print())
					.andExpect(status().is3xxRedirection()).andReturn();

		MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
		
		mvcResult = mockMvc.perform(get("/login").session(session))
         								.andDo(print())
         								//.andExpect(cookie().exists("JSESSIONID"))
         								.andExpect(status().isOk()).andReturn();
		
		session = (MockHttpSession) mvcResult.getRequest().getSession();
		
		mvcResult = mockMvc.perform(post("/challenge").session(session))
					.andDo(print())
					//.andExpect(cookie().exists("JSESSIONID"))
					.andExpect(status().isOk()).andReturn();
		 
	}
}
