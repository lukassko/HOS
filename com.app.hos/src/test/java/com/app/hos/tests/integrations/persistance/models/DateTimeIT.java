package com.app.hos.tests.integrations.persistance.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.tests.integrations.config.PersistanceConfig;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.hibernate.annotations.Type;
import org.junit.Assert;

//@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class})
public class DateTimeIT {

	@PersistenceContext
	private EntityManager em;

	@Test
	@Transactional
	public void saveTestEntityAndCheckIfDateTimeIsValid () {
		// given
		DateTime time = new DateTime();
		TestDateTimeEntity entityBeforeSave = new TestDateTimeEntity(time);
		TestDateTimeEntity entityAfterRetrive = null;
		
		// when
		em.persist(entityBeforeSave);
		em.flush();
		
		em.clear();
		
		Query query = em.createQuery("SELECT dt FROM TestDateTimeEntity dt");
		entityAfterRetrive = (TestDateTimeEntity)query.getSingleResult();
	
		// then
		DateTime selectedTime = entityAfterRetrive.getTime();
		Assert.assertEquals(time, selectedTime);
	}
	
	@Table(name = "test_datetime")
	@Entity(name = "TestDateTimeEntity")
	public static class TestDateTimeEntity extends BaseEntity {
		
		private static final long serialVersionUID = 1L;
		
		@Type(type = "com.app.hos.persistance.custom.DateTimeUserType")
		private DateTime time;
		
		public TestDateTimeEntity() {}
		
		public TestDateTimeEntity(DateTime time) {
			this.time = time;
		}

		public DateTime getTime() {
			return time;
		}
 	}
}
