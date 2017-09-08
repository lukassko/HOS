package com.app.hos.utils.converters;

import java.util.Date;

import com.app.hos.share.utils.DateTime;

public class DateTimeConverter {

	public static Date getDate(DateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
    	return new Date(dateTime.getTimestamp());
    }

	public static DateTime getDateTime(Date date) {
		if (date == null) {
			return null;
		}
    	return new DateTime(date.getTime());
    }

}
