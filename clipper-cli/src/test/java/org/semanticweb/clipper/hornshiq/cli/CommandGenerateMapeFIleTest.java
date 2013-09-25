package org.semanticweb.clipper.hornshiq.cli;

import org.junit.Test;

public class CommandGenerateMapeFIleTest {

	@Test
	public void test() {

		ClipperApp.main(String.format(
				"gen src/test/resources/lubm-ex-20/University1_1.owl -jdbcUrl=jdbc:postgresql://localhost/dlvdb_university -user=xiao")
				.split("\\ "));

	}
}
