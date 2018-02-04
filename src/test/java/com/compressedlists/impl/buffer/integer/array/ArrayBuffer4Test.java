package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;

public class ArrayBuffer4Test extends AbstractBufferTest {
	
	public int getNumStates() {
		return 16;
	}

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new UnsignedIntArrayBuffer4();
	}
	
	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE;
	}

	@Override
	protected int getWaistedSize(int size) {
		return 0;
	}
}
