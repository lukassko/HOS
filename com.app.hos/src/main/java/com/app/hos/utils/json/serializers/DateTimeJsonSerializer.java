package com.app.hos.utils.json.serializers;

import java.io.IOException;

import com.app.hos.persistance.custom.DateTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateTimeJsonSerializer extends StdSerializer<DateTime> {

	private static final long serialVersionUID = 1L;
	
	public DateTimeJsonSerializer(Class<DateTime> dateTime) {
        super(dateTime);
    }
	
	@Override
	public void serialize(DateTime date, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("year", date.getYear());
		gen.writeNumberField("month", date.getMonth());
		gen.writeNumberField("day", date.getDay());
		gen.writeNumberField("hour", date.getHour());
		gen.writeNumberField("minutes", date.getMinutes());
		gen.writeNumberField("seconds", date.getSeconds());
		//gen.writeNumberField("millis", date.getMillis());
		gen.writeEndObject();
	}

}
