package com.app.hos.share.utils;

import java.io.Serializable;

import org.joda.time.field.FieldUtils;

public class DateTime implements Serializable {

	private static final long serialVersionUID = 3L;

	private transient org.joda.time.DateTime dateTime;
	private final Integer hour;
	private final Integer minutes;
	private final Integer seconds;
	private final Integer millis;
	private final Integer day;
	private final Integer month;
	private final Integer year;

	public DateTime() {
		this.dateTime = new org.joda.time.DateTime();
		this.hour = dateTime.getHourOfDay();
		this.minutes = dateTime.getMinuteOfHour();
		this.seconds = dateTime.getSecondOfMinute();
		this.millis = dateTime.getMillisOfSecond();
		this.day = dateTime.getDayOfMonth();
		this.month = dateTime.getMonthOfYear();
		this.year = dateTime.getYear();
	}
	
	public DateTime(int year,int month,int day,int hour,int minutes,int seconds, int millis) {
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
		this.millis = millis;
		this.day = day;
		this.month = month;
		this.year = year;
		this.dateTime = new org.joda.time.DateTime(year, month, day, hour, minutes, seconds,millis); 
	}

	public org.joda.time.DateTime getDateTime () {
		if (dateTime == null) {
			this.dateTime = new org.joda.time.DateTime(this.year,this.month, this.day, 
								this.hour, this.minutes, this.seconds,millis);  
		}
		return this.dateTime;
	}
	
	@Override
	public int hashCode() {
		return getDateTime().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		DateTime other = (DateTime) obj;
		return getDateTime().equals(other.getDateTime());
	}

	@Override
	public String toString() {
		return "DateTime [hour=" + hour + ", minutes=" + minutes + ", seconds=" + seconds
				+ ", day=" + day + ", month=" + month + ", year=" + year + "]";
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

	public Integer getMillis() {
		return millis;
	}

}
