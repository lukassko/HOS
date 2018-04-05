package com.app.hos.tests.integrations.security;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import com.app.hos.config.ApplicationContextConfig;
import com.app.hos.config.WebSecurityConfig;
import com.app.hos.utils.embeddedserver.EmbeddedTomcat;
import com.app.hos.utils.embeddedserver.WarDeployer;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.FileUtils;

//@Ignore("run only one integration test")
//@Profile("web-integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes = {WebSocketConfig.class})
@ContextConfiguration(classes = {ApplicationContextConfig.class, WebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TomcatSecurityIT {

	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private static EmbeddedTomcat tomcat;
	
	//private static String tomcatWorkingDir = System.getProperty("java.io.tmpdir");
	private static String tomcatWorkingDir = ".";
	
	private static String appName = "HOS";
	
	private static WarDeployer deployer;
	
    @BeforeClass
    public static void setupTomcat() throws Exception {
        tomcat = new EmbeddedTomcat();
        tomcat.initInstance();
        
/*        File webApp = new File(tomcatWorkingDir, appName);
        File oldWebApp = new File(webApp.getAbsolutePath());
        FileUtils.deleteDirectory(oldWebApp);
        deployer = new WarDeployer("HOS");
        deployer.deployWarToLocation(new File(tomcatWorkingDir + "/" + appName + ".war"));
        */
        
		tomcat.start();
    }
    
    @AfterClass
    public static void shutDownTomcat() throws LifecycleException  {
 		tomcat.stop();
    }
    
    @Test
    public void stage00_checkIfEmbeddedTomactHasStarted()  {
        Assert.assertTrue(tomcat.isStarted());
    }

    @Test
    public void stage10_testHttpUnit() throws IOException, SAXException {
    	System.out.println("stage10_testHttpUnit START!");
    	WebConversation wc = new WebConversation();
    	WebResponse resp = wc.getResponse("http://localhost:8080/HOS/");
    	LOG.info(Integer.toString(resp.getResponseCode()));
    	LOG.info(resp.getText());
    }
}
