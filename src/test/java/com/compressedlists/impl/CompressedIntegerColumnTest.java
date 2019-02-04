package com.compressedlists.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompressedIntegerColumnTest {

	@Test
	public void testAddValue() {
		IntListImpl column = new IntListImpl();
		for (int i = 0; i < 1000000; i++) {
			column.addValue(i);
			assertEquals(i, column.getValue(i));
		}
		
		for (int i = 0; i < 1000000; i++) {
			assertEquals(i, column.getValue(i));
		}
	}

}
