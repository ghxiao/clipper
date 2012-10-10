package org.semanticweb.clipper.hornshiq.cli;

import org.junit.Test;

public class CommandInitDBTest {

	@Test
	public void test() {

		ClipperApp.main(String.format(
				"init src/test/resources/lubm-ex-20/LUBM-ex-20.owl -jdbcUrl=jdbc:postgresql://localhost/dlvdb_university -user=xiao")
				.split("\\ "));

	}
}
