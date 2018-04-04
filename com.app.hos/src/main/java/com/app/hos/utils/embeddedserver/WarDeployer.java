package com.app.hos.utils.embeddedserver;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class WarDeployer {

	private WebArchive archive;
	private static final String WEBAPP_SRC = "src/main/webapp";

	public WarDeployer(String archiveNane) {
		String name = archiveNane + ".war";
		archive = ShrinkWrap.create(WebArchive.class, name);
		archive.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
	}
}
