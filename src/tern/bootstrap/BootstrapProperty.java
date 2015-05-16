package tern.bootstrap;

import java.util.ArrayList;
import java.util.List;

public class BootstrapProperty extends BootstrapItem {

	private final String name;
	private final boolean staticProperty;
	private final List<IType> types;

	public BootstrapProperty(String name, boolean staticProperty) {
		this.name = name;
		this.staticProperty = staticProperty;
		this.types = new ArrayList<IType>();
	}

	public String getName() {
		return name;
	}

	public void addType(IType type) {
		this.types.add(type);
	}

	public List<IType> getTypes() {
		return types;
	}

	public boolean isStaticProperty() {
		return staticProperty;
	}

	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasSeveralTypesWithFn() {
		for (IType type : types) {
			if (type instanceof FunctionType) {
				return true;
			}
		}
		return false;
	}
}
