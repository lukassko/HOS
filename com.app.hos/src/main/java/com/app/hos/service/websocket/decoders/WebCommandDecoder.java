package com.app.hos.service.websocket.decoders;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.utils.json.JsonConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class WebCommandDecoder implements Decoder.Text<WebCommand>  {

	public void init(EndpointConfig endpointConfig) {}

	public void destroy() {}

	public WebCommand decode(String s) throws DecodeException {
		WebCommand cmd = null;
		try {
			cmd = JsonConverter.getObject(s, WebCommand.class);
		} catch (JsonParseException | JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cmd;
	}

	public boolean willDecode(String s) {
		return (s != null);
	}

}
