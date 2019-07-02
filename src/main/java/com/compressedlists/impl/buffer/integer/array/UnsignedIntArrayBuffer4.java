package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class UnsignedIntArrayBuffer4 extends UnsignedIntArrayBuffer8 implements IIntMemoryBuffer {

	
	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - size) + (size * 4)/8;
	}
	
	@Override
	public int getLogOfBitsPerRow() {
		return 2;
	}
}
