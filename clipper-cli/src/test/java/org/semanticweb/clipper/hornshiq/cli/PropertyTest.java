package org.semanticweb.clipper.hornshiq.cli;

import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Test;

public class PropertyTest {
	@Test
	public void testProperty() {
		Properties properties = System.getProperties();

		String path_sep = System.getProperty("path.separator");

		for (Entry<Object, Object> p : properties.entrySet()) {
			System.out.println(p.getKey() + " = " + p.getValue());
		}
		System.out.println();
		System.out.println("ENV : ");
		for (Entry<String, String> e : System.getenv().entrySet()) {
			System.out.println(e.getKey() + " = " + e.getValue());
		}
	}
}
