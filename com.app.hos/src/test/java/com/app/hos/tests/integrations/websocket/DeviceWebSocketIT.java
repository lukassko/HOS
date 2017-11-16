package com.app.hos.tests.integrations.websocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.app.hos.config.WebSocketConfig;
import java.lang.reflect.Type;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

// NEED TO START TOMCAT SERVER WITH INTEGRATION TESTS

//@Ignore("run only one integration test")
@ActiveProfiles("integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebSocketConfig.class})
public class DeviceWebSocketIT {

	private static Tomcat tomcat;
	private static final int TOMCAT_PORT = 8080;
	  
	private static final String WEBSOCKET_URI = "ws://localhost:8080/HOS/websocket";
	private static final String WEBSOCKET_TOPIC = "/topic";
	
    private BlockingQueue<String> blockingQueue;
    private WebSocketStompClient stompClient;
    
    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
        		asList(new WebSocketTransport(new StandardWebSocketClient()))));
        
        tomcat = new Tomcat();
        String baseDir = ".";
        tomcat.setPort(TOMCAT_PORT);
        tomcat.setBaseDir(baseDir);
        tomcat.getHost().setAppBase(baseDir);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.getHost().setAutoDeploy(true);
        tomcat.addWebapp(tomcat.getHost(), "/HOS", "src/main/webapp");
        try {
        	//tomcat.init();
            tomcat.start();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION BEFORE" + e.getMessage());
			e.printStackTrace();
		}
        
        
    }
    
    public void deploy(String appName) {
        tomcat.addWebapp(tomcat.getHost(), "/" + appName, "src/main/webapp");
    }

    //public String getApplicationUrl(String appName) {
   // 	return String.format("http://%s:%d/%s", tomcat.getHost().getName(),tomcat.getConnector().getLocalPort(), appName);
   // }
      
    @After
    public void shutDownTomcat()  {
      try {
		tomcat.stop();
		tomcat.destroy();
	} catch (LifecycleException e) {
		System.out.println("EXCEPTION AFTER" + e.getMessage());
		e.printStackTrace();
	}
    }
    
    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        String message = "MESSAGE TEST";
        
        session.send(WEBSOCKET_TOPIC, message.getBytes());
        String receiveMessage = blockingQueue.poll(1, SECONDS);
        Assert.assertEquals(message, receiveMessage);
    }
    
    private class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
