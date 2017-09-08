package com.app.hos.service.managers.connection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.HistoryConnection;
import com.app.hos.persistance.repository.ConnectionRepository;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.exceptions.HistoryConnectionException;

@Service
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ConnectionManager {

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private ConnectionRepository connectionRepository;

	
	public boolean closeConnection(String connectionId) {
		if (closeTcpConnection(connectionId)) {
			generateHistoryConnection(connectionId);
			return true;
		}
		return false;
	}

	public Connection findConectionByid(String connectionId) {
		return connectionRepository.findConnectionById(connectionId);
	}
	
	// update connection time in 'connection' table, add HistoryConnection
	public void generateHistoryConnection(String connectionId){
		Connection connection = connectionRepository.findConnectionById(connectionId);
		connection.setEndConnectionTime(new DateTime());
		try {
			HistoryConnection historyConnection = connection.createHistoryConnection();
			connectionRepository.save(historyConnection);
		} catch (HistoryConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnectionOpen (String connectionId) {
		List<String> conectionIds = getConnectionFactory().getOpenConnectionIds();
		return conectionIds.contains(connectionId);
	}
	
	public boolean closeTcpConnection(String connectionId) {
		return getConnectionFactory().closeConnection(connectionId);
	}
	
	private AbstractConnectionFactory getConnectionFactory() {
		return (AbstractConnectionFactory)appContext.getBean("hosServer");
	}

}
