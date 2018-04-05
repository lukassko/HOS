package com.app.hos.utils.embeddedserver;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;

public class WarDeployer {

	private WebArchive archive;
	
	private final String WEBAPP_SRC = "src/main/webapp";
	
	private final String WEBINF_SRC = "WEB-INF/web.xml";
	
	public WarDeployer() {
		this("test");
	}
	
	public WarDeployer(String archiveNane) {
		archive = ShrinkWrap.create(WebArchive.class, archiveNane + ".war");
	}
	
	public void setWebXML () {
		archive.setWebXML(new File(WEBAPP_SRC, WEBINF_SRC));
	}
	
	public void addPackage(String pkg) {
		archive.addPackage(java.lang.Package.getPackage(pkg));
	}
	
	public void addPackageByPattern(String pattern) {
		// TODO
	}
	
	public void deployWarToLocation(File location) {
		new ZipExporterImpl(archive).exportTo(location, true);
	}
}
