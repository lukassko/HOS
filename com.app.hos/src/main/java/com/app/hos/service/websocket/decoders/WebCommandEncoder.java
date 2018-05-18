package com.app.hos.service.websocket.decoders;
import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.utils.json.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class WebCommandEncoder implements Encoder.Text<WebCommand> {

	public void init(EndpointConfig endpointConfig) {}

	public void destroy() {}

	public String encode(WebCommand object) throws EncodeException {
		try {
			return JsonConverter.getJson(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
