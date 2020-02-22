package com.compressedlists.impl.buffer.integer.bitset;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;

public class BitSetBuffer1Test extends AbstractBufferTest {

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new BitSetMemoryBuffer1();
	}

	@Override
	protected int getNumStates() {
		return 2;
	}

	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE/8;
	}

	@Override
	protected int getWaistedSize(int size) {
		return 0;
	}

}
