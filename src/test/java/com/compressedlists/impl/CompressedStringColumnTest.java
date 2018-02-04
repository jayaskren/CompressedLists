package com.compressedlists.impl;

import junit.framework.TestCase;

public class CompressedStringColumnTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddValue() {
		CompressLivStringListImpl column = new CompressLivStringListImpl();
		int numRows = 1000;
		for(int i=0; i<numRows ; i++) {
			column.addValue("Test " + i);
			assertEquals("Failed at row "  + i, i+1, column.getSize());
			assertEquals("Failed at row "  + i, "Test " + i, column.getValue(i));
		}
		
		for(int i=0; i<numRows ; i++) {
			assertEquals("Test " + i, column.getValue(i));
		}
	}

}
