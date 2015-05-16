package tern.bootstrap;

public class FunctionType extends BootstrapMethod implements IType {

	public FunctionType(String signature, String returnFn) {
		super(signature, new SimpleType(returnFn), false);
	}

}
