package com.compressedlists.impl.buffer;

import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer1;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer16;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer2;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer32;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer4;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer8;
import com.compressedlists.impl.buffer.integer.bitset.BitSetMemoryBuffer1;
import com.compressedlists.impl.buffer.integer.bitset.BitSetMemoryBuffer2;
import com.compressedlists.impl.buffer.integer.bitset.BitSetMemoryBuffer4;
import com.compressedlists.impl.buffer.integer.unsafe.SignedUnsafeBuffer32;
import com.compressedlists.impl.buffer.integer.unsafe.UnsignedUnsafeBuffer16;
import com.compressedlists.impl.buffer.integer.unsafe.UnsignedUnsafeBuffer8;

import junit.framework.TestCase;

public class BufferFactoryTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateArrayBufferTypeInt() {
		ArrayBufferFactory factory = new ArrayBufferFactory();
		IMemoryBuffer buffer1 = factory.createBuffer(0);
		assertEquals(UnsignedIntArrayBuffer1.class, buffer1.getClass());
		IMemoryBuffer buffer2 = factory.createBuffer(1);
		assertEquals(UnsignedIntArrayBuffer2.class, buffer2.getClass());
		IMemoryBuffer buffer4 = factory.createBuffer(2);
		assertEquals(UnsignedIntArrayBuffer4.class, buffer4.getClass());
		IMemoryBuffer buffer8 = factory.createBuffer(3);
		assertEquals(UnsignedIntArrayBuffer8.class, buffer8.getClass());
		IMemoryBuffer buffer16 = factory.createBuffer(4);
		assertEquals(UnsignedIntArrayBuffer16.class, buffer16.getClass());
		IMemoryBuffer buffer32 = factory.createBuffer(5);
		assertEquals(UnsignedIntArrayBuffer32.class, buffer32.getClass());
		assertEquals(4, BitUtil.logSlow(BitUtil.logSlow(32647)) + 1);
				
	}

	public void testCreateUnsafeBufferTypeInt() {
		UnsafeBufferFactory factory= new UnsafeBufferFactory();
		IMemoryBuffer buffer1 = factory.createBuffer(0);
		assertEquals(BitSetMemoryBuffer1.class, buffer1.getClass());
		IMemoryBuffer buffer2 = factory.createBuffer(1);
		assertEquals(BitSetMemoryBuffer2.class, buffer2.getClass());
		IMemoryBuffer buffer4 = factory.createBuffer(2);
		assertEquals(BitSetMemoryBuffer4.class, buffer4.getClass());
		IMemoryBuffer buffer8 = factory.createBuffer(3);
		assertEquals(UnsignedUnsafeBuffer8.class, buffer8.getClass());
		IMemoryBuffer buffer16 = factory.createBuffer(4);
		assertEquals(UnsignedUnsafeBuffer16.class, buffer16.getClass());
		IMemoryBuffer buffer32 = factory.createBuffer(5);
		assertEquals(SignedUnsafeBuffer32.class, buffer32.getClass());
		assertEquals(4, BitUtil.logSlow(BitUtil.logSlow(32647)) + 1);
				
	}
}
