package com.app.hos.server.messaging;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public class MessageBuilderTest {
	
	@Test
	public void buildingMessageWithPayloadShouldReturnMessageWithSamePayload() throws Exception {
		// given
		Object payload = new Object();
		
		// when
		Message<Object> message = MessageBuilder.withPayload(payload).build();
		
		// then
		assertTrue(payload == message.getPayload());
	}
	
	@Test
	public void buildingMessageWithHeadersShouldRetrunMessageWithSameHedaers() throws Exception {
		// given
		Object headerValue = new Object();
		
		// when
		Message<Object> message = MessageBuilder.withPayload(new Object()).addHeader(IpHeaders.CONNECTION_ID, headerValue).build();
		
		// then
		MessageHeaders headers = message.getHeaders();
		assertTrue(headerValue == headers.get(IpHeaders.CONNECTION_ID));
	}
	
	@Test
	public void removeMessageFromHeaderBuilderShouldReturnMessageWithoutThatHeader() throws Exception {
		// given
		Object headerValue = new Object();
				
		// when
		MessageBuilder<Object> builder = MessageBuilder.withPayload(new Object())
											.addHeader(IpHeaders.IP_ADDRESS, headerValue)
											.addHeader(IpHeaders.CONNECTION_ID, new Object());
			
		Message<Object> message = builder.removeHeader(IpHeaders.IP_ADDRESS).build();
				
		// then
		MessageHeaders headers = message.getHeaders();
		assertNull(headers.get(IpHeaders.IP_ADDRESS));
	}
	
	@Test
	public void setHeaderIfAbsentShouldNotAddHeaderAndNotReplaceCurrentValueIfHeaderAllreadyExists() throws Exception {
		/// given
		Object ipAdd1 = new Object();
		Object ipAdd2 = new Object();
				
		// when
		MessageBuilder<Object> builder = MessageBuilder.withPayload(new Object())
											.addHeader(IpHeaders.IP_ADDRESS, ipAdd1);
			
		Message<Object> message = builder.setHeaderIfAbsent(IpHeaders.IP_ADDRESS, ipAdd2).build();
				
		// then
		MessageHeaders headers = message.getHeaders();
		assertTrue(ipAdd1 == headers.get(IpHeaders.IP_ADDRESS));
		assertFalse(ipAdd2 == headers.get(IpHeaders.IP_ADDRESS));
	}
}
