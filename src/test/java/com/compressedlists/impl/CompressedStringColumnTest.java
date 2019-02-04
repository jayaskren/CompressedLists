package com.compressedlists.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompressedStringColumnTest {

	@Test
	public void testAddValue() {
		CompressLivStringListImpl column = new CompressLivStringListImpl();
		int numRows = 100000;
		for(int i=0; i<numRows ; i++) {
			column.addValue("Test " + i);
			assertEquals(i+1, column.getSize(), "Failed at row "  + i);
			assertEquals("Test " + i, column.getValue(i), "Failed at row "  + i);
		}
		
		for(int i=0; i<numRows ; i++) {
			assertEquals("Test " + i, column.getValue(i));
		}
	}

}
