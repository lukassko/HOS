package com.app.hos.service.websocket.decoders;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.utils.json.JsonConverter;

public class WebCommandEncoder implements Encoder.Text<WebCommand> {

	public void init(EndpointConfig endpointConfig) {}

	public void destroy() {}

	public String encode(WebCommand object) throws EncodeException {
		System.out.println("ENCODE");
		return JsonConverter.getJson(object);
	}

}
