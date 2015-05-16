package tern.bootstrap;

public class SimpleType implements IType {

	private String type;

	public SimpleType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
