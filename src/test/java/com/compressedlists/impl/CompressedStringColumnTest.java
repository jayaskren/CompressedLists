package com.compressedlists.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompressedStringColumnTest {

	@Test
	public void testAddValue() {
		TextListImpl column = new TextListImpl();
		int numRows = 100000;
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
