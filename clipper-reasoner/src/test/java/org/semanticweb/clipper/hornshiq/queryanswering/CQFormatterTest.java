package org.semanticweb.clipper.hornshiq.queryanswering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CQFormatterTest {

	@Test
	public void testUmlauts(){
		assertEquals("Oesterreich", CQFormatter.replaceUmlauts("Österreich")); 
	}
}
