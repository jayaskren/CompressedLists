package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class UnsignedIntArrayBuffer32 implements IIntMemoryBuffer {

	protected int[] data;
	protected int size;
	
	public UnsignedIntArrayBuffer32 () {
		
		data = new int[BUFFER_SIZE];
	}
	
	@Override
	public void addValue(int value) {
		data[size++] = value;
	}
	
	@Override
	public void setValue(int pos, int value) {
		data[pos] = value;
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
		return BUFFER_SIZE * 4;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - size) * 4;
	}

	@Override
	public int getNumBits() {
		return 5;
	}
	
	@Override
	public void reset() {
		size = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size = other.getSize();
		for (int i=0; i < this.size; i++) {
			data[i] = other.getValue(i);
		}
	}
}
