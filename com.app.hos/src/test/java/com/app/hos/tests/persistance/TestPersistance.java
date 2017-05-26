package com.app.hos.tests.persistance;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class TestPersistance {
	
	//protected static EntityManagerFactory emf;
   // protected static EntityManager em;
    
    @BeforeClass
    public static void init() throws FileNotFoundException, SQLException {
      //  emf = Persistence.createEntityManagerFactory("test");
   //     em = emf.createEntityManager();
    }
    
    @AfterClass
    public static void tearDown(){
       // em.clear();
      //  em.close();
      //  emf.close();
    }
}
