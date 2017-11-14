package com.app.hos.service.websocket.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.utils.json.JsonConverter;

public class WebCommandDecoder implements Decoder.Text<WebCommand>  {

	public void init(EndpointConfig endpointConfig) {}

	public void destroy() {}

	public WebCommand decode(String s) throws DecodeException {
		return JsonConverter.getObject(s, WebCommand.class);
	}

	public boolean willDecode(String s) {
		return (s != null);
	}

}
