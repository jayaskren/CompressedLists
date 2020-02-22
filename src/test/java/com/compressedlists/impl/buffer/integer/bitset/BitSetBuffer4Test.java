package com.compressedlists.impl.buffer.integer.bitset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;

public class BitSetBuffer4Test extends AbstractBufferTest {

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new BitSetMemoryBuffer4();
	}

	@Override
	protected int getNumStates() {
		return 16;
	}

	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE/2;
	}

	@Override
	protected int getWaistedSize(int size) {
		return 0;
	}

	public void testSet() {
		BitSetMemoryBuffer4 buffer = new BitSetMemoryBuffer4();
		buffer.addValue(0);
		assertFalse(buffer._getBit(0));
		assertFalse(buffer._getBit(1));
		assertFalse(buffer._getBit(2));
		assertFalse(buffer._getBit(3));
		assertEquals(0, buffer.getValue(0));
		buffer.addValue(1);
		assertTrue(buffer._getBit(4));
		assertFalse(buffer._getBit(5));
		assertFalse(buffer._getBit(6));
		assertFalse(buffer._getBit(7));
		assertEquals(1, buffer.getValue(1));
		buffer.addValue(2);
		assertFalse(buffer._getBit(8));
		assertTrue(buffer._getBit(9));
		assertFalse(buffer._getBit(10));
		assertFalse(buffer._getBit(11));
		assertEquals(2, buffer.getValue(2));
		buffer.addValue(3);
		assertTrue(buffer._getBit(13));
		assertTrue(buffer._getBit(13));
		assertFalse(buffer._getBit(15));
		assertFalse(buffer._getBit(16));
		assertEquals(3, buffer.getValue(3));
		//TODO
//		buffer.addValue(4);
//		assertFalse(buffer._getBit(0));
//		assertFalse(buffer._getBit(1));
//		assertEquals(4, buffer.getValue(0));
//		buffer.addValue(5);
//		assertTrue(buffer._getBit(2));
//		assertFalse(buffer._getBit(3));
//		assertEquals(5, buffer.getValue(1));
//		buffer.addValue(6);
//		assertFalse(buffer._getBit(4));
//		assertTrue(buffer._getBit(5));
//		assertEquals(6, buffer.getValue(2));
//		buffer.addValue(7);
//		assertTrue(buffer._getBit(6));
//		assertTrue(buffer._getBit(7));
//		assertEquals(7, buffer.getValue(3));
	}
}
