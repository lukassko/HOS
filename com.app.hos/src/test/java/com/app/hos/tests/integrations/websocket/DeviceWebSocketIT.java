package com.app.hos.tests.integrations.websocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.app.hos.utils.EmbeddedTomcat;

import java.lang.reflect.Type;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.apache.catalina.LifecycleException;

@Ignore("run only one integration test")
@Profile("web-integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes = {WebSocketConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceWebSocketIT {
	
	private static final String WEBSOCKET_URI = "ws://localhost:8080/HOS/websocket";
	private static final String WEBSOCKET_TOPIC = "/topic";
	private static final String WEBSOCKET_APP = "/app";
	
	private static EmbeddedTomcat tomcat;
    private BlockingQueue<String> blockingQueue;
    private WebSocketStompClient stompClient;

   
    @BeforeClass
    public static void setupClass() {
        tomcat = new EmbeddedTomcat();
        tomcat.init();
        try {
			tomcat.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
    }
    
    @Before
    public void setupTest() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
        		asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }
      
    @AfterClass
    public static void shutDownTomcat()  {
    	 try {
 			tomcat.stop();
 		} catch (LifecycleException e) {
 			e.printStackTrace();
 		}
    }
    
    @Test
    public void stage00_checkIfEmbeddedTomactHasStarted() throws LifecycleException {
        if (!tomcat.isStarted()) {
        	throw new LifecycleException();
        }
    }

    //@Test
    public void stage10_shouldReceiveAMessageFromTheServerUsingTopic() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        String message = "MESSAGE TEST";
        Thread.sleep(2000);
        session.send(WEBSOCKET_TOPIC, message.getBytes());
        String receiveMessage = blockingQueue.poll(1, SECONDS);
        Assert.assertEquals(message, receiveMessage);
    }
    
    @Test
    public void stage20_shouldReceiveAMessageFromTheServerUsingApp() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
        session.subscribe(WEBSOCKET_APP, new DefaultStompFrameHandler());

        String message = "MESSAGE TEST";
        
        session.send(WEBSOCKET_APP, message.getBytes());
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
