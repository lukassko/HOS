package com.app.hos.service.managers.connection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
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
	private ConnectionRepository connectionRepository;

	public Connection findConectionByid(String connectionId) {
		return connectionRepository.findConnectionById(connectionId);
	}
	
	public Collection<HistoryConnection> findAllHistoryConnectionsByDeviceId(int id) {
		return connectionRepository.findAllHistoryConnectionsByDeviceId(id);
	}
	
	// update connection time in 'connection' table, add HistoryConnection
	public void finalizeConnection(String connectionId){
		Connection connection = connectionRepository.findConnectionById(connectionId);
		connection.setEndConnectionTime(new DateTime());
		try {
			HistoryConnection historyConnection = connection.createHistoryConnection();
			connectionRepository.save(historyConnection);
		} catch (HistoryConnectionException e) {
			e.printStackTrace();
		}
	}
	
}
