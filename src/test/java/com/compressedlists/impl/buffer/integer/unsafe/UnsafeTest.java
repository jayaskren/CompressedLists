package com.compressedlists.impl.buffer.integer.unsafe;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

public class UnsafeTest {

@SuppressWarnings("restriction")
private static final Unsafe unsafe;
	
	static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	@Test
	public void testUnsafe() {
		int[] data = new int[1024];
		int numBytesPerRow = 4;
		long address = unsafe.arrayBaseOffset(byte[].class);
		
		for (int i=0; i< data.length ; i++) {
			unsafe.putInt(data, address + i * numBytesPerRow, i);
			assertEquals(i, unsafe.getInt(data, address + (i)*numBytesPerRow), "Failed when i = " + i);
			assertEquals(i, data[i], "Failed when i = " + i);
		}
		
		for (int i=0; i< data.length ; i++) {
			assertEquals(i, unsafe.getInt(data, address + (i)*numBytesPerRow), "Failed when i = " + i);
			assertEquals(i, data[i], "Failed when i = " + i);
		}
	}

}
