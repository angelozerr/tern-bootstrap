package tern.bootstrap;

import java.util.ArrayList;
import java.util.Collection;

public class BootstrapClass extends BootstrapItem {

	private final String className;
	private boolean objectLiteral;
	private final boolean privateClass;
	private final String superClass;
	private final Collection<BootstrapMethod> methods;
	private final Collection<BootstrapProperty> properties;

	public BootstrapClass(String className, String superClass,
			boolean objectLiteral, boolean privateClass) {
		this.className = className;
		this.superClass = superClass;
		this.objectLiteral = objectLiteral;
		this.privateClass = privateClass;
		this.methods = new ArrayList<BootstrapMethod>();
		this.properties = new ArrayList<BootstrapProperty>();
	}

	public String getClassName() {
		return className;
	}

	public boolean isObjectLiteral() {
		return objectLiteral;
	}

	public boolean isPrivateClass() {
		return privateClass;
	}

	public String getSuperClass() {
		return superClass;
	}

	public String getUrl() {
		return null;
	}

	public BootstrapMethod addMethod(BootstrapMethod method) {
		methods.add(method);
		return method;
	}

	public Collection<BootstrapMethod> getMethods() {
		return methods;
	}

	public void addProperty(BootstrapProperty property) {
		properties.add(property);
	}

	public Collection<BootstrapProperty> getProperties() {
		return properties;
	}

}
