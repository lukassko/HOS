package com.app.hos.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistance/mysql.properties" })
@ComponentScan({ "com.app.hos.persistance" })
public class PersistanceConfig {

	@Autowired
	private Environment env;
	
	@Value("classpath:persistance/initDB.sql")
	private Resource schemaScript;

	//@Value("classpath:com/foo/sql/db-test-data.sql")
	//private Resource dataScript;

	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
	   LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	   sessionFactory.setDataSource(restDataSource());
	   sessionFactory.setPackagesToScan("com.app.hos.persistance.models");
	   sessionFactory.setHibernateProperties(hibernateProperties());
	    return sessionFactory;
	}
	
	@Bean
	public DataSource restDataSource() {
	   BasicDataSource dataSource = new BasicDataSource();
	   dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	   dataSource.setUrl(env.getProperty("jdbc.url"));
	   dataSource.setUsername(env.getProperty("jdbc.user"));
	   dataSource.setPassword(env.getProperty("jdbc.pass"));
	   return dataSource;
	}
	 
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
	   HibernateTransactionManager txManager = new HibernateTransactionManager();
	   txManager.setSessionFactory(sessionFactory);
	
	   return txManager;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}

	private DatabasePopulator databasePopulator() {
	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(schemaScript);
	    //populator.addScript(dataScript);
	    return populator;
	}
	
	Properties hibernateProperties() {
	      return new Properties() {
	         {
	            setProperty("hibernate.hbm2ddl.auto",
	              env.getProperty("hibernate.hbm2ddl.auto"));
	            setProperty("hibernate.dialect",
	              env.getProperty("hibernate.dialect"));
	            setProperty("hibernate.globally_quoted_identifiers",
	             "true");
	         }
	      };
	   }
	
}
