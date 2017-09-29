package com.app.hos.config.repository;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource({ "classpath:persistance/properties/sqlite.properties" })
@ComponentScan("com.app.hos.logging.*")
@EnableTransactionManagement
public class SqlitePersistanceConfig {

	@Autowired
	private Environment env;

	@Bean(name = "sqliteEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("sqlite_persistance");
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.app.hos.logging.*" });
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(hibernateProperties());
		em.afterPropertiesSet();
		return em.getObject();
	}
	
	@Bean(name = "sqliteEntityManager")
    public EntityManager entityManager(@Qualifier("sqliteEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
	
	@Bean(name = "sqliteDataSource")
	public DataSource dataSource() {
	   BasicDataSource dataSource = new BasicDataSource();
	   dataSource.setDriverClassName(env.getProperty("sqlite.jdbc.driverClassName"));
	   dataSource.setUrl(env.getProperty("sqlite.jdbc.url"));
	   dataSource.setUsername(env.getProperty("sqlite.jdbc.user"));
	   dataSource.setPassword(env.getProperty("sqlite.jdbc.pass"));
	   //dataSource.setInitialSize(Integer.parseInt(env.getProperty("sqlite.hibernate.initial_size")));
	   //dataSource.setMaxActive(Integer.parseInt(env.getProperty("sqlite.hibernate.max_active")));
	   //dataSource.setMaxIdle(Integer.parseInt(env.getProperty("sqlite.hibernate.max_idle")));
	   //dataSource.setMinIdle(Integer.parseInt(env.getProperty("sqlite.hibernate.min_idle")));
	   return dataSource;
	}
	 
	@Bean(name = "sqliteJpaTransactionManager")
    JpaTransactionManager transactionManager(@Qualifier("sqliteEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

	Properties hibernateProperties() {
	      return new Properties() {
	         {
	            setProperty("hibernate.hbm2ddl.auto",
	              env.getProperty("sqlite.hibernate.hbm2ddl.auto"));
	            setProperty("hibernate.hbm2ddl.import_files",
		  	      env.getProperty("sqlite.hibernate.hbm2ddl.import_files"));
	            setProperty("hibernate.dialect",
	              env.getProperty("sqlite.hibernate.dialect"));
	            setProperty("hibernate.globally_quoted_identifiers",
	             "true");
	         }
	      };
	   }
	
}
