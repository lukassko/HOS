package com.app.hos.service.managers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.persistance.repository.ConnectionRepository;
import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.service.exceptions.handler.ExceptionUtils;

@Service
@Transactional
public class ConnectionManager {
	
	@Autowired
	private ConnectionRepository connectionRepository;

	public Connection findConnection(String connectionId) {
		Connection connection;
		try {
			connection = connectionRepository.find(connectionId);
		} catch (IllegalArgumentException e) {
			// no device find
			connection = null;
		}
		return connection;
	}
	
	public Collection<HistoryConnection> findAllHistoryConnectionsByDeviceId(int id) {
		return connectionRepository.findAllHistoryConnectionsByDeviceId(id);
	}
	
	// update connection time in 'connection' table, add HistoryConnection
	public void finalizeConnection(String connectionId){
		Connection connection = connectionRepository.find(connectionId);
		connection.setEndConnectionTime(new DateTime());
		try {
			HistoryConnection historyConnection = HistoryConnection.getInstance(connection);
			connectionRepository.save(historyConnection);
		} catch (HistoryConnectionException e) {
			ExceptionUtils.handle(e);
		}
	}
	
}
