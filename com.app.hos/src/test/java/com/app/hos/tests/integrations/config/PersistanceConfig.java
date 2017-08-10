package com.app.hos.tests.integrations.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.app.hos.persistance.*")
@EnableTransactionManagement
public class PersistanceConfig {

	@Configuration
	@Profile("test-hsqldb")
	@PropertySource({ "classpath:persistance/properties/hsqldb.properties" })
    static class Hsqldb
    { }
	
	//@Profile({"test-sqlite","integration-test"})
	@Configuration
	@Profile({"test-sqlite"})
	@PropertySource({ "classpath:persistance/properties/sqlite-test.properties" })
    static class SQLite
    { }
	
	@Configuration
	@Profile({"integration-test"})
	@PropertySource({ "classpath:persistance/properties/mysql.properties" })
    static class Mysql
    { }
	
	@Autowired
	private Environment env;

	@Primary
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	   em.setDataSource(dataSource());
	   em.setPackagesToScan(new String[] { "com.app.hos.persistance.*" });
	   em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	   em.setJpaProperties(hibernateProperties());
	   em.afterPropertiesSet();
	   return em.getObject();
	}
		
	
	@Primary
	@Bean(name = "testEntityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
	
	@Primary
	@Bean
	public DataSource dataSource() {
	   BasicDataSource dataSource = new BasicDataSource();
	   dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	   dataSource.setUrl(env.getProperty("jdbc.url"));
	   dataSource.setUsername(env.getProperty("jdbc.user"));
	   dataSource.setPassword(env.getProperty("jdbc.pass"));
	   return dataSource;
	}

	@Primary
	@Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

	Properties hibernateProperties() {
	      return new Properties() {
	         {
	            setProperty("hibernate.hbm2ddl.auto",
	              env.getProperty("hibernate.hbm2ddl.auto"));
	            setProperty("hibernate.hbm2ddl.import_files",
		  	      env.getProperty("hibernate.hbm2ddl.import_files"));
	            setProperty("hibernate.dialect",
	              env.getProperty("hibernate.dialect"));
	            setProperty("hibernate.globally_quoted_identifiers",
	             "true");
	         }
	      };
	   }
	
}
