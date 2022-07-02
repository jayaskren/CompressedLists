package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;

public class ArrayBuffer2Test extends AbstractBufferTest {
	
	public int getNumStates() {
		return 4;
	}

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new UnsignedIntArrayBuffer2();
	}
	
	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE;
	}

	@Override
	protected int getWaistedSize(int size) {
		// TODO Auto-generated method stub
		return 0;
	}
}
