package com.app.hos.server.factory;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TcpServerRule implements TestRule {

	@Override
	public Statement apply(Statement base, Description description) {
		// TODO: Start and close server before test
		return null;
	}

}
