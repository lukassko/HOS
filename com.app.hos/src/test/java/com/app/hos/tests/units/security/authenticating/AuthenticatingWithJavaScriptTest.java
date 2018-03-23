package com.app.hos.tests.units.security.authenticating;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Before;
import org.junit.Test;

public class AuthenticatingWithJavaScriptTest {

	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private ScriptEngine engine;
	
	@Before
    public void setUp() throws Exception {
		
		//URL url = this.getClass().getResource("D:/Java/Workspace/HOS/com.app.hos/src/main/webapp/resources/scripts/security.js");
		//File jsFile = new File("D:/Java/Workspace/HOS/com.app.hos/src/main/webapp/resources/scripts/security.js");
		File jsFile = new File("/resources/scripts/security.js");
		//File jsFile = new File(url.toURI());

	    InputStream targetStream = new FileInputStream(jsFile);   
        engine = new ScriptEngineManager().getEngineByName("JavaScript");
        engine.eval(new InputStreamReader(targetStream));
    }
	
	@Test
    public void comparingHashFromJavaAndJSShouldBeEquals() throws Exception {
        Invocable invocable = (Invocable)engine;
        Object result = invocable.invokeFunction("sha256", "password");
        LOG.info(result.toString());
    }
}
