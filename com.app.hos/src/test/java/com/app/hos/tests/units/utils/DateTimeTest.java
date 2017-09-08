package com.app.hos.tests.units.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.json.JsonConverter;
import com.app.hos.utils.json.deserializers.DateTimeJsonDeserializer;
import com.app.hos.utils.json.serializers.DateTimeJsonSerializer;

public class DateTimeTest {
	
	@Test
	public void jsonSerializationTest() {
		DateTime dateTime = new DateTime();
		String json = JsonConverter.getJson(dateTime);
		int year = dateTime.getYear();
		int month = dateTime.getMonth();
		int day = dateTime.getDay();
		int hour = dateTime.getHour();
		int minutes = dateTime.getMinutes();
		int seconds = dateTime.getSeconds();
		//int millis = dateTime.getMillis();
		StringBuilder expectedJson = new StringBuilder();
		expectedJson.append("{\"year\":" + year);
		expectedJson.append(",\"month\":" + month);
		expectedJson.append(",\"day\":" + day);
		expectedJson.append(",\"hour\":" + hour);
		expectedJson.append(",\"minutes\":" + minutes);
		expectedJson.append(",\"seconds\":" + seconds);
		//expectedJson.append(",\"millis\":" + millis +"}");
		Assert.assertEquals(json,expectedJson.toString());
	}
	
	@Test
	public void jsonDeserializationTest() {
		DateTime dateTime = new DateTime();
		String json = JsonConverter.getJson(dateTime);
		DateTime convertedTime = JsonConverter.getObject(json, DateTime.class);
		Assert.assertTrue(dateTime.equals(convertedTime));
		Assert.assertEquals(dateTime.toString(),convertedTime.toString());
	}
	
	@Test
	public void getTimestampTest() {
		DateTime dateTime = new DateTime();
		String json = JsonConverter.getJson(dateTime);
		DateTime convertedTime = JsonConverter.getObject(json, DateTime.class);
		Assert.assertEquals(dateTime.getTimestamp(),convertedTime.getTimestamp());
	}
	
	@Test 
	public void serializeToDiskTest() {
		DateTime dateBefore = null;
		DateTime dateAfter = null;
        try {
        	dateBefore = new DateTime();
            FileOutputStream fos = new FileOutputStream("tempdata.test");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dateBefore);
            oos.close();
        }
        catch (Exception ex) {}
         
        try {
            FileInputStream fis = new FileInputStream("tempdata.test");
            ObjectInputStream ois = new ObjectInputStream(fis);
            dateAfter = (DateTime) ois.readObject();
            ois.close();
        } catch (Exception ex) {
        } finally {
            new File("tempdata.test").delete();
        }

       Assert.assertEquals(dateBefore, dateAfter);
    }
}
