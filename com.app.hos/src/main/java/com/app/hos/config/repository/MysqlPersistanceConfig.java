package com.app.hos.config.repository;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource({ "classpath:persistance/properties/mysql.properties" })
@Configuration
@EnableTransactionManagement
public class MysqlPersistanceConfig {

	@Autowired
	private Environment env;

	@Value("classpath:persistance/queries/mysql/initDB.sql")
	private Resource schemaScript;

	@Primary
	@Bean(name = "mysqlEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	   em.setDataSource(dataSource());
	   em.setPackagesToScan(new String[] { "com.app.hos.persistance.repository.*" , "com.app.hos.persistance.models.*" });
	   em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	   em.setJpaProperties(hibernateProperties());
	   em.afterPropertiesSet();
	   return em.getObject();
	}
	
	@Primary
	@Bean(name = "mysqlEntityManager")
    public EntityManager entityManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
	
	@Primary
	@Bean(name = "mysqlDataSource")
	public DataSource dataSource() {
	   BasicDataSource dataSource = new BasicDataSource();
	   dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	   dataSource.setUrl(env.getProperty("jdbc.url"));
	   dataSource.setUsername(env.getProperty("jdbc.user"));
	   dataSource.setPassword(env.getProperty("jdbc.pass"));
	   return dataSource;
	}
	 
	@Primary
	@Bean(name = "mysqlJpaTransactionManager")
    JpaTransactionManager transactionManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

	private DatabasePopulator databasePopulator() {
	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(schemaScript);
	    return populator;
	}
	
	//@Primary	
	//@Bean
	public DataSourceInitializer dataSourceInitializer( @Qualifier("mysqlDataSource") final DataSource dataSource) {
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
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
