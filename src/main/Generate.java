package main;

import java.io.IOException;

import org.xml.sax.SAXException;

import tern.bootstrap.BootstrapApiHelper;
import tern.bootstrap.generator.BootstrapPluginGenerator;
import tern.bootstrap.generator.TernPluginOptions;

public class Generate {

	public static void main(String[] args) throws IOException, SAXException {
		String s = BootstrapApiHelper.generateFile();
		System.err.println(s);
	}
}
