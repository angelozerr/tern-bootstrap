package tern.bootstrap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BootstrapContentHandler extends DefaultHandler {

	private BootstrapApi api;

	private boolean collectClassName;
	private StringBuilder className = null;

	private BootstrapClass clazz;

	public BootstrapContentHandler(String name, String version, String baseUrl) {
		this.api = new BootstrapApi(name, version);
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		String cssClassName = attributes.getValue("class");
		if ("div".equalsIgnoreCase(name)
				&& "bs-docs-section".equals(cssClassName)) {
			this.clazz = null;
		} else {
			if (className == null) {
				// search class name
				if ("h1".equalsIgnoreCase(name)
						&& "page-header".equals(cssClassName)) {
					// className = new StringBuilder();
					collectClassName = true;
				} else if ("small".equalsIgnoreCase(name)) {
					if (collectClassName) {
						className = new StringBuilder();
					}
				}
			}

		}
		super.startElement(uri, localName, name, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if ("small".equalsIgnoreCase(name)) {
			if (className != null) {
				String s = className.toString();
				if (s.endsWith(".js")) {
					this.clazz = getClass("jQuery.fn", null, false, false);
					api.addClass(clazz);

					BootstrapMethod method = new BootstrapMethod(s.substring(0,
							s.length() - ".js".length()),
							new SimpleType("this"), true);
					clazz.addMethod(method);

					className = null;
					collectClassName = false;
				}
			}
		}
	}

	private BootstrapClass getClass(String className, String superClass,
			boolean objectLiteral, boolean privateClass) {
		// String className = getClassName(entryName, entryType);
		BootstrapClass clazz = api.getClass(className);
		if (clazz == null) {
			// String superClass = getSuperClass(entryType);
			clazz = new BootstrapClass(className, superClass, objectLiteral,
					privateClass);
			api.addClass(clazz);
		}
		return clazz;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (className != null) {
			className.append(ch, start, length);
		}
		super.characters(ch, start, length);
	}

	public BootstrapApi getApi() {
		return api;
	}
}
