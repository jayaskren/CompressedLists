package com.compressedlists.impl.buffer.integer.unsafe;

import java.util.Random;

import com.compressedlists.impl.buffer.integer.unsafe.AbstractUnsafeBuffer;
import com.compressedlists.impl.buffer.integer.unsafe.UnsignedUnsafeBuffer16;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UnsafeBuffer16Test {

	@Test
	public void testAddValue() {

		int[] values = new int[AbstractUnsafeBuffer.BUFFER_SIZE];
		
		UnsignedUnsafeBuffer16 buffer1 = new UnsignedUnsafeBuffer16();
		Random rand = new Random();
		for (int i=0; i < UnsignedUnsafeBuffer16.BUFFER_SIZE ; i++) {
			int val = i;//rand.nextInt(65536);
			values[i] = val;
			buffer1.addValue(val);
			assertEquals(val, buffer1.getValue(i), "Error when i = " + i);
		}
		
		for (int i=0; i < UnsignedUnsafeBuffer16.BUFFER_SIZE ; i++) {
			assertEquals(values[i], buffer1.getValue(i), "Error when i = " + i);
		}
	
	}

}
