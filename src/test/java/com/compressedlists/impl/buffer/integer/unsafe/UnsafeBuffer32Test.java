package com.compressedlists.impl.buffer.integer.unsafe;

import java.util.Random;

import com.compressedlists.impl.buffer.integer.unsafe.AbstractUnsafeBuffer;
import com.compressedlists.impl.buffer.integer.unsafe.SignedUnsafeBuffer32;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
			assertEquals(val, buffer1.getValue(i), "Error when i = " + i);
		}
		
		for (int i=0; i < SignedUnsafeBuffer32.BUFFER_SIZE ; i++) {
			assertEquals(values[i], buffer1.getValue(i), "Error when i = " + i);
		}
	
	}

}
