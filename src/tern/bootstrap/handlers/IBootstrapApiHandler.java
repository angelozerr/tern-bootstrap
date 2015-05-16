package tern.bootstrap.handlers;

import java.io.IOException;

import tern.bootstrap.BootstrapMethod;
import tern.bootstrap.BootstrapProperty;

public interface IBootstrapApiHandler {

	void startApi(String name, String version) throws IOException;

	void endApi() throws IOException;

	void startClass(String name, String superclass, boolean privateClass,
			String description, String url) throws IOException;

	void endClass() throws IOException;

	void handleMethod(BootstrapMethod method);

	void handleProperty(BootstrapProperty property);
}
