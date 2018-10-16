package com.app.hos.config.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;

@Configuration
public class MongoDbPersistanceConfig extends AbstractMongoConfiguration {

	@Override
	public MongoClient mongoClient() {
		return new MongoClient("127.0.0.1");
	}

	@Override
	protected String getDatabaseName() {
		return "HOS";
	}
	
}
