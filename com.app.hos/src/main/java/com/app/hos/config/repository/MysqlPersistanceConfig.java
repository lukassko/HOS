package com.app.hos.config.repository;

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

@Profile("!web-integration-test")
@Configuration
@PropertySource({ "classpath:persistance/properties/mysql.properties" })
@ComponentScan("com.app.hos.persistance.*")
@EnableTransactionManagement
public class MysqlPersistanceConfig {

	@Autowired
	private Environment env;
	
	@Primary
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPersistenceUnitName("myslq_persistance");
		emf.setDataSource(dataSource());
		emf.setPackagesToScan(new String[] { "com.app.hos.persistance.*" });
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setJpaProperties(hibernateProperties());
		emf.afterPropertiesSet();
		return emf.getObject();
	}
	
	@Primary
	@Bean
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
	            setProperty("hibernate.show_sql", 
	              env.getProperty("hibernate.show_sql"));
	            setProperty("hibernate.format_sql", 
	  	          env.getProperty("hibernate.format_sql"));
	            setProperty("hibernate.dialect",
	              env.getProperty("hibernate.dialect"));
	            //setProperty("hibernate.globally_quoted_identifiers",
	            // "true");
	         }
	      };
	   }
	
}
