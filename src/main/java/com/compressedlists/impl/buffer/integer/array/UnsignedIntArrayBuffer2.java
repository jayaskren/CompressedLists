package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class UnsignedIntArrayBuffer2 extends UnsignedIntArrayBuffer8 implements IIntMemoryBuffer {

	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - size) + (size * 6)/8;
	}
}
