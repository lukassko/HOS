package com.app.hos.utils.json.serializers;

import java.io.IOException;
import java.util.Date;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.utils.converters.DateTimeConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateJsonSerializer extends StdSerializer<Date> {

	private static final long serialVersionUID = 1L;
	
	public DateJsonSerializer(Class<Date> date) {
        super(date);
    }
	
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
		DateTime dateTime = DateTimeConverter.getDateTime(date);
		gen.writeStartObject();
		gen.writeNumberField("year", dateTime.getYear());
		gen.writeNumberField("month", dateTime.getMonth());
		gen.writeNumberField("day", dateTime.getDay());
		gen.writeNumberField("hour", dateTime.getHour());
		gen.writeNumberField("minutes", dateTime.getMinutes());
		gen.writeNumberField("seconds", dateTime.getSeconds());
		gen.writeEndObject();
	}

}
