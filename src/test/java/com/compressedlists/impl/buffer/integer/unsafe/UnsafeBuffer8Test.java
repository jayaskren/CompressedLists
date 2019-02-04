package com.compressedlists.impl.buffer.integer.unsafe;

import java.util.Random;

import com.compressedlists.impl.buffer.integer.unsafe.AbstractUnsafeBuffer;
import com.compressedlists.impl.buffer.integer.unsafe.UnsignedUnsafeBuffer8;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UnsafeBuffer8Test {

	public void testAddValue8() {
		int[] values = new int[AbstractUnsafeBuffer.BUFFER_SIZE];
		
		UnsignedUnsafeBuffer8 buffer1 = new UnsignedUnsafeBuffer8();
		Random rand = new Random();
		for (int i=0; i < UnsignedUnsafeBuffer8.BUFFER_SIZE ; i++) {
			int val = rand.nextInt(256);
			values[i] = val;
			buffer1.addValue(val);
			assertEquals(val, buffer1.getValue(i), "Error when i = " + i);
		}
		
		for (int i=0; i < UnsignedUnsafeBuffer8.BUFFER_SIZE ; i++) {
			assertEquals(values[i], buffer1.getValue(i), "Error when i = " + i);
		}
	
	}

}
