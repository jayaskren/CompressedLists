package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;

public class ArrayBuffer32Test extends AbstractBufferTest {
	
	public int getNumStates() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new UnsignedIntArrayBuffer32();
	}
	
	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE * 4;
	}

	@Override
	protected int getWaistedSize(int size) {
		// TODO Auto-generated method stub
		return 0;
	}
}
