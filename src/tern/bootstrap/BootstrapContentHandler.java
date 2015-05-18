package tern.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import tern.bootstrap.utils.StringUtils;

public class BootstrapContentHandler extends DefaultHandler {

	private BootstrapApi api;

	private boolean collectClassName;
	private StringBuilder className = null;

	private BootstrapClass clazz;

	private List<String> propertyOptionRow;

	private BootstrapMethod method;

	private StringBuilder tdContent;

	private StringBuilder doc;

	private String classURL;

	public BootstrapContentHandler(String name, String version, String baseUrl) {
		this.api = new BootstrapApi(name, version);
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		String cssClass = attributes.getValue("class");
		String id = attributes.getValue("id");
		if ("div".equalsIgnoreCase(name) && "bs-docs-section".equals(cssClass)) {
			this.clazz = null;
			propertyOptionRow = null;
			collectClassName = false;
		} else if ("td".equalsIgnoreCase(name)) {
			tdContent = new StringBuilder();
		} else if ("p".equalsIgnoreCase(name)) {
			if (method != null && StringUtils.isEmpty(method.getDescription())) {
				doc = new StringBuilder();
			}
		} else {
			if (className == null) {
				// search class name
				if ("h1".equalsIgnoreCase(name)
						&& "page-header".equals(cssClass)) {
					classURL = "http://getbootstrap.com/javascript/#" + id; 
					collectClassName = true;
				} else if ("small".equalsIgnoreCase(name)) {
					if (collectClassName) {
						className = new StringBuilder();
					}
				} else if ("h3".equalsIgnoreCase(name)) {
					if (id != null && id.endsWith("-options")) {
						// <h3 id="popovers-options">Options</h3>
						propertyOptionRow = new ArrayList<String>();
					} else {
						propertyOptionRow = null;
					}
				}

				if (propertyOptionRow != null && propertyOptionRow.size() > 0) {
					if ("tr".equalsIgnoreCase(name)) {
						String propName = propertyOptionRow.get(0);
						String propTypes = propertyOptionRow.get(1);
						String propDoc = propertyOptionRow.get(3);
						BootstrapProperty property = new BootstrapProperty(
								propName, true);

						String[] types = propTypes.split("[|]");
						for (int i = 0; i < types.length; i++) {
							property.addType(new SimpleType(types[i].trim()));
						}
						property.setDescription(propDoc);
						BootstrapClass optionClass = getClass(method.getName()
								+ "Option", null, true, true);
						if (method.getParameters().size() == 0) {
							BootstrapParameter parameter = new BootstrapParameter(
									"options", true);
							parameter.addType(new SimpleType(optionClass
									.getClassName()));
							method.addParameter(parameter);
						}
						optionClass.addProperty(property);
						propertyOptionRow.clear();
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
					method = new BootstrapMethod(s.substring(0, s.length()
							- ".js".length()), new SimpleType("this"), true);
					method.setUrl(classURL);
					clazz.addMethod(method);

					className = null;
					collectClassName = false;
				}
			}
		} else if ("td".equalsIgnoreCase(name)) {
			if (propertyOptionRow != null) {
				propertyOptionRow.add(tdContent.toString());
			}
			tdContent = null;
		} else if ("p".equalsIgnoreCase(name)) {
			if (doc != null) {
				method.setDescription(doc.toString());
				doc = null;
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
		if (tdContent != null) {
			tdContent.append(ch, start, length);
			// if (propertyOptionRow != null) {
			// propertyOptionRow.add(String.valueOf(ch, start, length));
			// }
		} else if (className != null) {
			className.append(ch, start, length);
		} else if (doc != null) {
			doc.append(ch, start, length);
		}
		super.characters(ch, start, length);
	}

	public BootstrapApi getApi() {
		return api;
	}
}
