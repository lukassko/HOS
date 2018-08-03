package com.app.hos.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.app.hos.utils.security.SecurityUtils;

@Ignore
public class HashingWithJavaScriptAndJavaIT {

	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private ScriptEngine engine;
	private InputStream targetStream;
	
	@Before
    public void setUp() throws Exception {
		try {
			File jsFile = new File("src/main/webapp/resources/scripts/security.js");
		    targetStream = new FileInputStream(jsFile);   
	        engine = new ScriptEngineManager().getEngineByName("JavaScript");
	        engine.eval(new InputStreamReader(targetStream));
		} catch (IOException e) {}
    }
	
	@After
    public void cleanUp() throws Exception {
		try {
			if (targetStream != null)
				targetStream.close();
		} catch (IOException e) {}
    }
	
	@Test
    public void hashFromJavaAndJSShouldBeEquals() throws Exception {
        Invocable invocable = (Invocable)engine;
        String jsHash = (String)invocable.invokeFunction("sha256", "password");
        String javaHash = SecurityUtils.hash("password");
        Assert.assertEquals(jsHash, javaHash);
    }
	
	@Test
    public void hashFromJavaAndJSShouldBeEqualsWithConcat() throws Exception {
        Invocable invocable = (Invocable)engine;
        String challnege = "+F6naz6TXSitusBJvuovf6ZtATrjAJZPRD2fboRwvcM=";
        String hash = "5593220c751cd6ad0ac4f220f6f08e133652a5d58412f5b895a61d5860f5f7ae"; 
        String jsHash = (String)invocable.invokeFunction("sha256", hash + challnege);
        String javaHash = SecurityUtils.hash(hash + challnege);
        Assert.assertEquals(jsHash, javaHash);
    }
	
	@Test
    public void hashFromJavaAndJSShouldBeDifferents() throws Exception {
        Invocable invocable = (Invocable)engine;
        String jsHash = (String)invocable.invokeFunction("sha256", "password1");
        String javaHash = SecurityUtils.hash("password2");
        Assert.assertNotEquals(jsHash, javaHash);
    }
	
	
}
