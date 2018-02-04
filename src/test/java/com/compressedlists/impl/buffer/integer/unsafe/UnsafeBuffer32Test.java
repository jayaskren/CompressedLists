package com.compressedlists.impl.buffer.integer.unsafe;

import java.util.Random;

import com.compressedlists.impl.buffer.integer.unsafe.AbstractUnsafeBuffer;
import com.compressedlists.impl.buffer.integer.unsafe.SignedUnsafeBuffer32;

import junit.framework.TestCase;

public class UnsafeBuffer32Test extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddValue() {
		int[] values = new int[AbstractUnsafeBuffer.BUFFER_SIZE];
		
		SignedUnsafeBuffer32 buffer1 = new SignedUnsafeBuffer32();
		Random rand = new Random();
		for (int i=0; i < SignedUnsafeBuffer32.BUFFER_SIZE ; i++) {
			int val = rand.nextInt(Integer.MAX_VALUE);
			values[i] = val;
			buffer1.addValue(val);
			assertEquals("Error when i = " + i, val, buffer1.getValue(i));
		}
		
		for (int i=0; i < SignedUnsafeBuffer32.BUFFER_SIZE ; i++) {
			assertEquals("Error when i = " + i, values[i], buffer1.getValue(i));
		}
	
	}

}
