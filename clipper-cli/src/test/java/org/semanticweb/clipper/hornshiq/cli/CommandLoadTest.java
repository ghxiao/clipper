package org.semanticweb.clipper.hornshiq.cli;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandLoadTest {

	@Test
	public void test() {

		ClipperApp.main(String.format(
				"load src/test/resources/lubm-ex-20/University1_1.owl -jdbcUrl=jdbc:postgresql://localhost/dlvdb_university")
				.split("\\ "));

	}
}
