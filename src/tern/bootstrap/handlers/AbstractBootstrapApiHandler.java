package tern.bootstrap.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public abstract class AbstractBootstrapApiHandler implements
		IBootstrapApiHandler {

	private final OutputStream out;
	private final Writer writer;

	public AbstractBootstrapApiHandler(OutputStream out) {
		this.out = out;
		this.writer = null;
	}

	public AbstractBootstrapApiHandler(Writer writer) {
		this.out = null;
		this.writer = writer;
	}

	protected void write(String s) throws IOException {
		if (out != null) {
			out.write(s.getBytes());
		} else if (writer != null) {
			writer.write(s);
		}
	}

	public Writer getWriter() {
		return writer;
	}
}
