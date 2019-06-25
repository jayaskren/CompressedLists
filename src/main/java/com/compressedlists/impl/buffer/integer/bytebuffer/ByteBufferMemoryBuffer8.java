package com.compressedlists.impl.buffer.integer.bytebuffer;

import java.nio.ByteBuffer;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class ByteBufferMemoryBuffer8 implements IIntMemoryBuffer {

	protected ByteBuffer buffer;
	protected int size;
	
	public ByteBufferMemoryBuffer8 (boolean useDirect) {
		if (useDirect) {
			buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		} else {
			buffer = ByteBuffer.allocate(BUFFER_SIZE);
		}
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSizeInBytes() {
		return BUFFER_SIZE;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return BUFFER_SIZE - size;
	}

	@Override
	public void addValue(int value) {
		buffer.put((byte)value);
		size++;
	}

	@Override
	public void setValue(int pos, int value) {
		buffer.put(pos, (byte)value);
	}

	@Override
	public int getValue(int pos) {
		return buffer.get(pos);
	}

	@Override
	public int getNumBits() {
		return 3;
	}

	@Override
	public void reset() {
		buffer.clear();
		size = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		int size = other.getSize();
		for (int i=0; i < size ; i++) {
			buffer.put(i, (byte)other.getValue(i));
		}
	}

}
