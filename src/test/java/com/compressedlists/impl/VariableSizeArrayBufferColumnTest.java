package com.compressedlists.impl;

import java.util.Random;

import junit.framework.TestCase;
public class VariableSizeArrayBufferColumnTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddValue() {
		System.out.println(Math.pow(2, 16));
		TextListImpl column = new TextListImpl();
		
		int numValuesToAdd = 100000;
		for (int i=0; i < numValuesToAdd; i++) {
			if (i== 65536) {
				System.out.println("Adding: " +i);
			}
			column.addValue("Test " + Integer.toString(i));
		}
		
		for (int i=0; i<numValuesToAdd; i++) {
			if(i >= Math.pow(2, 16) - 1) {
				assertEquals("Error when i = " + i, "[Other]", column.getValue(i));
			} else {
				assertEquals("Test " + Integer.toString(i), column.getValue(i));
			}
		}
		column  = new TextListImpl();
		
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
		
		column  = new TextListImpl();
		Random rand = new Random();
		for (int i=0; i< 500000; i++) {
			boolean val = true;//i%2 == 1;
			column.addValue(Boolean.toString(val));
			assertEquals("Error at row " + i ,Boolean.toString(val), column.getValue(i));
		}
		
		column  = new TextListImpl();
		rand = new Random();
		for (int i=0; i< 500000; i++) {
			boolean val = i%2 == 1;
			column.addValue(Boolean.toString(val));
			assertEquals("Error at row " + i ,Boolean.toString(val), column.getValue(i));
		}
	}
	
	public void testReuseBuffers() {
		TextListImpl column100000 = new TextListImpl();
		TextListImpl column256 = new TextListImpl();
		TextListImpl column16 = new TextListImpl();
		
		
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
