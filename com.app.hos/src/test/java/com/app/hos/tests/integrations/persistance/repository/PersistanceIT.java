package com.app.hos.tests.integrations.persistance.repository;

import java.io.FileNotFoundException;
import java.sql.SQLException;


import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class PersistanceIT {
	
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
