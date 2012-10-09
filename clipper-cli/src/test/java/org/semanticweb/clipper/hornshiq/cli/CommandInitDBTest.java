package org.semanticweb.clipper.hornshiq.cli;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandInitDBTest {

	@Test
	public void test() {

		ClipperApp.main(String.format(
				"init -jdbcUrl=jdbc:postgresql://localhost/dlvdb_university -user=xiao")
				.split("\\ "));

	}
}
