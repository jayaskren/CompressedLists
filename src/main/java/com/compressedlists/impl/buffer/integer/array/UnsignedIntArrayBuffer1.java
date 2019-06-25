package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class UnsignedIntArrayBuffer1 implements IIntMemoryBuffer {

	protected byte[] data;
	protected int size;
	
	public UnsignedIntArrayBuffer1 () {
		data = new byte[BUFFER_SIZE];
	}
	
	@Override
	public void addValue(int value) {
		data[size++] = (byte)value;
	}
	
	@Override
	public void setValue(int pos, int value) {
		data[pos] = (byte)value;
	}

	@Override
	public int getValue(int pos) {
		return data[pos];
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
		return (BUFFER_SIZE - size) +(size*7)/8;
	}

	@Override
	public int getNumBits() {
		return 0;
	}

	@Override
	public void reset() {
		size = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size = other.getSize();
		for (int i=0; i < this.size; i++) {
			data[i] = (byte)(0b11111111 & other.getValue(i));
		}
	}
	
	
}
