package com.app.hos.utils.json.deserializers;

import java.io.IOException;

import com.app.hos.persistance.custom.DateTime;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class DateTimeJsonDeserializer extends StdDeserializer<DateTime> {

	private static final long serialVersionUID = 1L;
	
	public DateTimeJsonDeserializer(Class<DateTime> dateTime) {
        super(dateTime);
    }
	
	@Override
	public DateTime deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
        int year = (Integer) ((IntNode) node.get("year")).numberValue();
        int month = (Integer) ((IntNode) node.get("month")).numberValue();
        int day = (Integer) ((IntNode) node.get("day")).numberValue();
        int hour = (Integer) ((IntNode) node.get("hour")).numberValue();
        int minutes = (Integer) ((IntNode) node.get("minutes")).numberValue();
        int seconds = (Integer) ((IntNode) node.get("seconds")).numberValue();
        return new DateTime(year,month,day,hour,minutes,seconds);
	}

}
