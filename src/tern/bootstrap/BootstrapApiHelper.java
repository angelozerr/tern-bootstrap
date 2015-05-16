package tern.bootstrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;

import org.cyberneko.html.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tern.bootstrap.generator.BootstrapPluginGenerator;
import tern.bootstrap.generator.TernPluginOptions;
import tern.bootstrap.handlers.IBootstrapApiHandler;
import tern.bootstrap.handlers.TernDefBootstrapApiHandler;

public class BootstrapApiHelper {

	public static String generate() throws IOException, SAXException {

		StringWriter defs = new StringWriter();
		File file = new File("getbootstrap.com/javascript.html");
		BootstrapApi api = BootstrapApiHelper.load(file);
		BootstrapApiHelper.visit(api, new TernDefBootstrapApiHandler(defs));

		String pluginName = "bootstrap";

		TernPluginOptions options = new TernPluginOptions(pluginName,
				defs.toString());
		return new BootstrapPluginGenerator().generate(options);
	}

	public static void visit(BootstrapApi api, IBootstrapApiHandler visitor)
			throws IOException {
		visitor.startApi(api.getName(), api.getVersion());
		// Loop for entries
		Collection<BootstrapClass> classes = api.getClasses();
		for (BootstrapClass clazz : classes) {
			visitor.startClass(clazz.getClassName(), clazz.getSuperClass(),
					clazz.isPrivateClass(), clazz.getDescription(),
					clazz.getUrl());
			// Loop for properties
			Collection<BootstrapProperty> properties = clazz.getProperties();
			for (BootstrapProperty property : properties) {
				visitor.handleProperty(property);
			}
			// Loop for methods
			Collection<BootstrapMethod> methods = clazz.getMethods();
			for (BootstrapMethod method : methods) {
				visitor.handleMethod(method);
			}

			visitor.endClass();
		}
		visitor.endApi();
	}

	public static BootstrapApi load(File file) throws SAXException, IOException {
		return load(new InputSource(new FileInputStream(file)), "bootstrap",
				"", "http://getbootstrap.com/javascript/");
	}

	public static BootstrapApi load(InputSource in, String name,
			String version, String baseUrl) throws SAXException, IOException {
		SAXParser saxReader = new SAXParser();
		// set the feature like explained in documentation :
		// http://nekohtml.sourceforge.net/faq.html#fragments
		saxReader
				.setFeature(
						"http://cyberneko.org/html/features/balance-tags/document-fragment",
						true);
		BootstrapContentHandler handler = new BootstrapContentHandler(name,
				version, baseUrl);
		saxReader.setContentHandler(handler);
		saxReader.parse(in);

		return handler.getApi();
	}

	public static String generateFile() throws IOException, SAXException {
		String plugin = generate();
		File file = new File("bootstrap.js");
		Writer writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(plugin);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		return plugin;
	}
}
