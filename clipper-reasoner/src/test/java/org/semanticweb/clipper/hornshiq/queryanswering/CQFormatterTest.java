package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.*;

import org.junit.Test;

public class CQFormatterTest {

	@Test
	public void testUmlauts(){
		assertEquals("Oesterreich", CQFormatter.replaceUmlauts("Österreich")); 
	}
}
