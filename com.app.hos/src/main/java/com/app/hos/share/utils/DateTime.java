package com.app.hos.share.utils;

import java.io.Serializable;

import com.app.hos.utils.json.JsonSerializer;
import com.app.hos.utils.json.serializers.DateTimeJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonSerializer(serializer = DateTimeJsonSerializer.class)
public class DateTime implements Serializable, Comparable<DateTime> {

	private static final long serialVersionUID = 4L;

	@JsonIgnore
	private transient org.joda.time.DateTime dateTime;
	private final Integer hour;
	private final Integer minutes;
	private final Integer seconds;
	//private final Integer millis;
	private final Integer day;
	private final Integer month;
	private final Integer year;
	private final Long timestamp;
	
	public DateTime() {
		this.dateTime = new org.joda.time.DateTime();
		this.hour = dateTime.getHourOfDay();
		this.minutes = dateTime.getMinuteOfHour();
		this.seconds = dateTime.getSecondOfMinute();
		//this.millis = dateTime.getMillisOfSecond();
		this.day = dateTime.getDayOfMonth();
		this.month = dateTime.getMonthOfYear();
		this.year = dateTime.getYear();
		long timestamp = dateTime.getMillis()/1000*1000;
		this.timestamp = timestamp;
	}
	
	public DateTime(long timestamp) {
		this.dateTime = new org.joda.time.DateTime(timestamp); 
		this.hour = dateTime.getHourOfDay();
		this.minutes = dateTime.getMinuteOfHour();
		this.seconds = dateTime.getSecondOfMinute();
		//this.millis = dateTime.getMillisOfSecond();
		this.day = dateTime.getDayOfMonth();
		this.month = dateTime.getMonthOfYear();
		this.year = dateTime.getYear();
		this.timestamp = timestamp;
	}
	
	public DateTime(int year,int month,int day,int hour,int minutes,int seconds) {
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
		//this.millis = millis;
		this.day = day;
		this.month = month;
		this.year = year;
		this.dateTime = new org.joda.time.DateTime(year, month, day, hour, minutes, seconds); 
		this.timestamp = dateTime.getMillis();
	}

	public org.joda.time.DateTime getJodaDateTime () {
		if (dateTime == null) {
			this.dateTime = new org.joda.time.DateTime(this.year,this.month, this.day, 
								this.hour, this.minutes, this.seconds);  
		}
		return this.dateTime;
	}
	
	@Override
	public int hashCode() {
		return getJodaDateTime().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		DateTime other = (DateTime) obj;
		return getTimestamp().equals(other.getTimestamp());
	}

	@Override
	public String toString() {
		return "DateTime [hour=" + hour + ", minutes=" + minutes + ", seconds=" + seconds
				+ ", day=" + day + ", month=" + month + ", year=" + year + ", timestamp="+ timestamp + "]";
	}

	public Integer getHour() {
		return hour;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public Integer getDay() {
		return day;
	}

	public Integer getMonth() {
		return month;
	}

	public Integer getYear() {
		return year;
	}

//	public Integer getMillis() {
//		return millis;
//	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	@Override
	public int compareTo(DateTime other) {
		return Long.compare(this.timestamp, other.timestamp);
	}
	
}
