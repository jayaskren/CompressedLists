package com.compressedlists.impl.buffer.integer.unsafe;

import java.util.Random;

import com.compressedlists.impl.buffer.integer.unsafe.AbstractUnsafeBuffer;
import com.compressedlists.impl.buffer.integer.unsafe.UnsignedUnsafeBuffer8;

import junit.framework.TestCase;

public class UnsafeBuffer8Test extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddValue8() {
		int[] values = new int[AbstractUnsafeBuffer.BUFFER_SIZE];
		
		UnsignedUnsafeBuffer8 buffer1 = new UnsignedUnsafeBuffer8();
		Random rand = new Random();
		for (int i=0; i < UnsignedUnsafeBuffer8.BUFFER_SIZE ; i++) {
			int val = rand.nextInt(256);
			values[i] = val;
			buffer1.addValue(val);
			assertEquals("Error when i = " + i, val, buffer1.getValue(i));
		}
		
		for (int i=0; i < UnsignedUnsafeBuffer8.BUFFER_SIZE ; i++) {
			assertEquals("Error when i = " + i, values[i], buffer1.getValue(i));
		}
	
	}

}
