package com.app.hos.tests.integrations.persistance.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.config.init.PostContextLoader;
import com.app.hos.config.repository.MysqlPersistanceConfig;
import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.ConnectionRepository;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.service.exceptions.handler.HOSExceptionHandlerFactory;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;
import com.app.hos.share.command.builder_v2.CommandFactory;
import com.app.hos.share.command.future.FutureCommandFactory;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.ApplicationContextProvider;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@Ignore("run only one integration test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = {
				ApplicationContextProvider.class,
				MysqlPersistanceConfig.class,
				PostContextLoader.class,
				CommandFactory.class,
				HOSExceptionHandlerFactory.class,
				FutureWebCommandFactory.class,
				FutureCommandFactory.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectionRepositoryIT {

	@Autowired
    private ConnectionRepository connectionRepository;
	
	// repository need to save connection with device
		// connection without device cannot exists
	@Autowired
    private DeviceRepository deviceRepository;
	
	
	
}
