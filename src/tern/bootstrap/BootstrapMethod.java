package tern.bootstrap;

import java.util.ArrayList;
import java.util.List;

public class BootstrapMethod extends BootstrapItem {

	private final String name;
	private final boolean staticMethod;
	private final List<BootstrapParameter> parameters;
	private IType returnValue;
	private String url;

	public BootstrapMethod(String name, IType returnValue, boolean staticMethod) {
		this.name = name;
		this.parameters = new ArrayList<BootstrapParameter>();
		this.returnValue = returnValue;
		this.staticMethod = staticMethod;
	}

	public String getName() {
		return name;
	}

	public boolean isStaticMethod() {
		return staticMethod;
	}

	public String getUrl() {
		return url;
	}

	public boolean hasReturnValue() {
		return returnValue != null;
	}

	public IType getReturnValue() {
		return returnValue;
	}

	public void addParameter(BootstrapParameter parameter) {
		parameters.add(parameter);
	}

	public List<BootstrapParameter> getParameters() {
		return parameters;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
