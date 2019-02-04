package com.compressedlists.impl.buffer.integer.bitset;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;
import com.compressedlists.impl.buffer.integer.bitset.BitSetMemoryBuffer2;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BitSetBuffer2Test extends AbstractBufferTest {

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new BitSetMemoryBuffer2();
	}

	@Override
	protected int getNumStates() {
		return 4;
	}

	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE/4;
	}

	@Override
	protected int getWaistedSize(int size) {
		return 0;
	}

	public void testSet() {
		BitSetMemoryBuffer2 buffer = new BitSetMemoryBuffer2();
		buffer.addValue(0);
		assertFalse(buffer._getBit(0));
		assertFalse(buffer._getBit(1));
		assertEquals(0, buffer.getValue(0));
		buffer.addValue(1);
		assertTrue(buffer._getBit(2));
		assertFalse(buffer._getBit(3));
		assertEquals(1, buffer.getValue(1));
		buffer.addValue(2);
		assertFalse(buffer._getBit(4));
		assertTrue(buffer._getBit(5));
		assertEquals(2, buffer.getValue(2));
		buffer.addValue(3);
		assertTrue(buffer._getBit(6));
		assertTrue(buffer._getBit(7));
		assertEquals(3, buffer.getValue(3));
	}
}
