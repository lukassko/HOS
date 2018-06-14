package com.app.hos.tests.integrations.persistance.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.tests.integrations.config.PersistanceConfig;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.Type;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateTimeIT {

	@PersistenceContext
	private EntityManager em;

	@Test
	@Transactional
	public void stage00_saveTestEntityAndCheckIfDateTimeIsValid () {
		// given
		DateTime time = new DateTime();
		TestDateTimeEntity_v1 entityBeforeSave = new TestDateTimeEntity_v1(time);
		TestDateTimeEntity_v1 entityAfterRetrive = null;
		
		// when
		em.persist(entityBeforeSave);
		em.flush();
		em.clear();		

		// then
		Query query = em.createQuery("SELECT dt FROM TestDateTimeEntity_v1 dt");
		entityAfterRetrive = (TestDateTimeEntity_v1)query.getSingleResult();
		DateTime selectedTime = entityAfterRetrive.getTime1();
		Assert.assertEquals(time, selectedTime);
	}
	
	@Test
	@Transactional
	public void stage05_saveTestEntityWithNullAsDateTime () {
		// given
		TestDateTimeEntity_v1 entityBeforeSave = new TestDateTimeEntity_v1(null);
		TestDateTimeEntity_v1 entityAfterRetrive = null;
		
		// when
		em.persist(entityBeforeSave);
		em.flush();
		em.clear();		

		// then
		Query query = em.createQuery("SELECT dt FROM TestDateTimeEntity_v1 dt WHERE dt.time1 IS NULL");
		entityAfterRetrive = (TestDateTimeEntity_v1)query.getSingleResult();
		Assert.assertNull(entityAfterRetrive.getTime1());
	}
	
	
	@Test
	@Transactional
	public void stage10_saveTestEntityWithMultiDateTimeFieldsAndCheckIfDateTimeIsValid () {
		// given
		DateTime time1 = new DateTime(1528987979);
		DateTime time2 = new DateTime(time1.getTimestamp() + 2000);
		TestDateTimeEntity_v2 entityBeforeSave = new TestDateTimeEntity_v2(time1,time2);
		TestDateTimeEntity_v2 entityAfterRetrive = null;
		
		// when
		em.persist(entityBeforeSave);
		em.flush();
		em.clear();
		
		// then
		Query query = em.createQuery("SELECT dt FROM TestDateTimeEntity_v2 dt");
		entityAfterRetrive = (TestDateTimeEntity_v2)query.getSingleResult();

		DateTime selectedTime1 = entityAfterRetrive.getTime1();
		DateTime selectedTime2 = entityAfterRetrive.getTime2();
		Assert.assertEquals(time1, selectedTime1);
		Assert.assertEquals(time2, selectedTime2);
	}

	@Test
	@Transactional
	public void stage20_saveTestEntityWichExtnedsOtherClassWithDateTimeFieldAndCheckIfDateTimeIsValid() {
		// given
		DateTime time1 = new DateTime();
		DateTime time3 = new DateTime(time1.getTimestamp() + 5000);
		TestDateTimeEntity_v3 entityBeforeSave = new TestDateTimeEntity_v3(time1,time3);
		TestDateTimeEntity_v3 entityAfterRetrive = null;
		
		// when
		em.persist(entityBeforeSave);
		em.flush();
		em.clear();
		
		// then
		Query query = em.createQuery("SELECT dt FROM TestDateTimeEntity_v3 dt");
		entityAfterRetrive = (TestDateTimeEntity_v3)query.getSingleResult();

		DateTime selectedTime1 = entityAfterRetrive.getTime1();
		DateTime selectedTime2 = entityAfterRetrive.getTime3();
		Assert.assertEquals(time1, selectedTime1);
		Assert.assertEquals(time3, selectedTime2);
	}
	
	@Table(name = "test_datetime_v1")
	@Entity(name = "TestDateTimeEntity_v1")
	@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) 
	public static class TestDateTimeEntity_v1 {
		
		@Id
		@GeneratedValue(strategy=GenerationType.TABLE)
		private Integer id;

		@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
		private DateTime time1;
		
		public TestDateTimeEntity_v1() {}
		
		public TestDateTimeEntity_v1(DateTime time1) {
			this.time1 = time1;
		}
		
		public DateTime getTime1() {
			return this.time1;
		}
		
		public void setTime1(DateTime time) {
			this.time1 = time;
		}
 	}
	
	@Table(name = "test_datetime_v2")
	@Entity(name = "TestDateTimeEntity_v2")
	public static class TestDateTimeEntity_v2 extends BaseEntity {
		
		private static final long serialVersionUID = 1L;
		
		@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
		private DateTime time1;
		
		@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
		private DateTime time2;
		
		public TestDateTimeEntity_v2() {}
		
		public TestDateTimeEntity_v2(DateTime time1, DateTime time2) {
			this.time1 = time1;
			this.time2 = time2;
		}

		public DateTime getTime1() {
			return this.time1;
		}
		
		public DateTime getTime2() {
			return this.time2;
		}
 	}
	
	@Table(name = "test_datetime_v3")
	@Entity(name = "TestDateTimeEntity_v3")
	public static class TestDateTimeEntity_v3 extends TestDateTimeEntity_v1 {

		@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
		private DateTime time3;
		
		public TestDateTimeEntity_v3() {}
		
		public TestDateTimeEntity_v3(DateTime time1, DateTime time3) {
			super(time1);
			this.time3 = time3;
		}
		
		public DateTime getTime3() {
			return this.time3;
		}
 	}
}
