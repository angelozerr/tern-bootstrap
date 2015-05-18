package tern.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class BootstrapApi {

	private final String name;
	private final String version;
	private final Map<String, BootstrapClass> classes;

	public BootstrapApi(String name, String version) {
		this.name = name;
		this.version = version;
		this.classes = new HashMap<String, BootstrapClass>();
	}

	public void loadEntry(InputStream in, String filename) throws SAXException,
			IOException, ParserConfigurationException {
		SAXParser saxReader = SAXParserFactory.newInstance().newSAXParser();
		// set the feature like explained in documentation :
		// http://nekohtml.sourceforge.net/faq.html#fragments
		/*
		 * saxReader .setFeature(
		 * "http://cyberneko.org/html/features/balance-tags/document-fragment",
		 * true);
		 */
		//EntryContentHandler handler = factory.create(this, filename);
		// saxReader.setContentHandler(handler);
		//saxReader.parse(new InputSource(in), handler);
	}

	public void addClass(BootstrapClass clazz) {
		classes.put(clazz.getClassName(), clazz);
	}

	public BootstrapClass getClass(String className) {
		return classes.get(className);
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public Collection<BootstrapClass> getClasses() {
		return classes.values();
	}

}
