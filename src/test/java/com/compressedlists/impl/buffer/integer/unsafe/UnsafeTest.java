package com.compressedlists.impl.buffer.integer.unsafe;

import java.lang.reflect.Field;

import junit.framework.TestCase;
import sun.misc.Unsafe;

public class UnsafeTest extends TestCase {

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
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testUnsafe() {
		int[] data = new int[1024];
		int numBytesPerRow = 4;
		long address = unsafe.arrayBaseOffset(byte[].class);
		
		for (int i=0; i< data.length ; i++) {
			unsafe.putInt(data, address + i * numBytesPerRow, i);
			assertEquals("Failed when i = " + i, i, unsafe.getInt(data, address + (i)*numBytesPerRow));
			assertEquals("Failed when i = " + i, i, data[i]);
		}
		
		for (int i=0; i< data.length ; i++) {
			assertEquals("Failed when i = " + i, i, unsafe.getInt(data, address + (i)*numBytesPerRow));
			assertEquals("Failed when i = " + i, i, data[i]);
		}
	}

}
