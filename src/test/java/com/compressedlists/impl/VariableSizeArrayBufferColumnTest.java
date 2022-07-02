package com.compressedlists.impl;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class VariableSizeArrayBufferColumnTest {

	@Test
	public void testAddValue() {
		System.out.println(Math.pow(2, 16));
		StringListImpl column = new StringListImpl();
		
		int numValuesToAdd = 65535; //100000;
		for (int i=0; i < numValuesToAdd; i++) {
			if (i== 65536) {
				System.out.println("Adding: " +i);
			}
			column.addValue("Test " + Integer.toString(i));
		}
		
		for (int i=0; i<numValuesToAdd; i++) {
			if(i >= Math.pow(2, 16) - 1) {
				// TODO Fix this
				assertEquals("Error when i = " + i, "[Other]", column.getValue(i));
			} else {
				assertEquals("Error when i = " + i, "Test " + Integer.toString(i), column.getValue(i));
			}
		}
		column  = new StringListImpl();
		
		for (int i=0; i < numValuesToAdd; i++) {
			for (int j=0; j < 10; j++) {
				column.addValue("Test " + Integer.toString(i));
			}
		}
		
		for (int i=0; i<column.getMaxUniqueValues()-1; i++) {
			for (int j=0; j < 10; j++) {
				assertEquals("Error at "+i + ", " + j, "Test " + Integer.toString(i) , column.getValue(i*10+j));
			}
		}
		
		column  = new StringListImpl();
		Random rand = new Random();
		for (int i=0; i< 500000; i++) {
			boolean val = true;//i%2 == 1;
			column.addValue(Boolean.toString(val));
			assertEquals("Error at row " + i , Boolean.toString(val), column.getValue(i));
		}
		
		column  = new StringListImpl();
		rand = new Random();
		for (int i=0; i< 500000; i++) {
			boolean val = i%2 == 1;
			column.addValue(Boolean.toString(val));
			assertEquals("Error at row " + i, Boolean.toString(val), column.getValue(i));
		}
	}
	
	@Test
	public void testReuseBuffers() {
		StringListImpl column100000 = new StringListImpl();
		StringListImpl column256 = new StringListImpl();
		StringListImpl column16 = new StringListImpl();
		
		
		int numValuesToAdd = 500000;
		
		for (int i=0; i < column100000.getMaxUniqueValues(); i++) {
			column100000.addValue("Test " + Integer.toString(i));
			assertEquals("Test " + Integer.toString(i), column100000.getValue(i));
		}
		
		for (int i=0; i < numValuesToAdd; i++) {
			column256.addValue("Test " + i%256);
			assertEquals("Test " + Integer.toString(i%256), column256.getValue(i));
		}
		
		for (int i=0; i < numValuesToAdd; i++) {
			column16.addValue("Test " + i%16);
			assertEquals("Test " + Integer.toString(i%16), column16.getValue(i));
		}
		
		// TODO fix this test
		for (int i=0; i < column100000.getMaxUniqueValues(); i++) {
			assertEquals("Test " + Integer.toString(i), column100000.getValue(i));
		}
		
		for (int i=0; i < numValuesToAdd; i++) {
			assertEquals("Test " + Integer.toString(i%256), column256.getValue(i));
		}
		
		for (int i=0; i < numValuesToAdd; i++) {
			assertEquals("Test " + Integer.toString(i%16), column16.getValue(i));
		}
	}
}
