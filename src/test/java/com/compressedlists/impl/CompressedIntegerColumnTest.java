package com.compressedlists.impl;

import junit.framework.TestCase;

public class CompressedIntegerColumnTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddValue() {
		IntListImpl column = new IntListImpl();
		for (int i = 0; i < 1000000; i++) {
			column.addValue(i);
//			System.out.println(i);
			assertEquals(i, column.getValue(i));
		}
		
		for (int i = 0; i < 1000000; i++) {
			assertEquals(i, column.getValue(i));
		}
	}

}
