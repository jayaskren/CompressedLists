package com.compressedlists.impl.buffer.integer.unsafe;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class UnsafeBuffer32Test {

	@Test
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
