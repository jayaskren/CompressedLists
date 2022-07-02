package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;

public class ArrayBuffer16Test extends AbstractBufferTest {
	
	public int getNumStates() {
		return 65536;
	}

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new UnsignedIntArrayBuffer16();
	}
	
	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE * 2;
	}

	@Override
	protected int getWaistedSize(int size) {
		return 0;
	}
}
