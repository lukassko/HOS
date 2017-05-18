package com.app.hos.utils;

public class DateTime {

	private final org.joda.time.DateTime dateTime;
	private final Integer hour;
	private final Integer minutes;
	private final Integer seconds;
	private final Integer day;
	private final Integer month;
	private final Integer year;
	
	public DateTime() {
		this.dateTime = new org.joda.time.DateTime();
		this.hour = dateTime.getHourOfDay();
		this.minutes = dateTime.getMinuteOfHour();
		this.seconds = dateTime.getSecondOfMinute();
		this.day = dateTime.getDayOfMonth();
		this.month = dateTime.getMonthOfYear();
		this.year = dateTime.getYear();
	}

	public org.joda.time.DateTime getDateTime () {
		return this.dateTime;
	}
	
	@Override
	public int hashCode() {
		return this.dateTime.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateTime other = (DateTime) obj;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		}
		return this.dateTime.equals(other.getDateTime());
	}

	@Override
	public String toString() {
		return "DateTime [dateTime=" + dateTime + ", hour=" + hour + ", minutes=" + minutes + ", seconds=" + seconds
				+ ", day=" + day + ", month=" + month + ", year=" + year + "]";
	}

	
}
