package com.compressedlists.impl.buffer.integer.array;


import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.integer.AbstractBufferTest;

public class ArrayBuffer1Test extends AbstractBufferTest {
	
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public int getNumStates() {
		return 2;
	}

	@Override
	protected IIntMemoryBuffer newBuffer() {
		return new UnsignedIntArrayBuffer1();
	}

	@Override
	protected int getSizeInBytes() {
		return IIntMemoryBuffer.BUFFER_SIZE;
	}

	@Override
	protected int getWaistedSize(int size) {
		return (IIntMemoryBuffer.BUFFER_SIZE - size);
	}
	
}
