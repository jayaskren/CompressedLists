package com.compressedlists.impl.buffer.integer.array;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class UnsignedIntArrayBuffer16 implements IIntMemoryBuffer {

	protected char[] data;
	protected int size;
	
	public UnsignedIntArrayBuffer16 () {
		data = new char[BUFFER_SIZE];
	}
	
	@Override
	public void addValue(int value) {
		data[size++] = (char)value;
	}
	
	@Override
	public void setValue(int pos, int value) {
		data[pos] = (char)value;
	}

	@Override
	public int getValue(int pos) {
		return (int)data[pos];
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSizeInBytes() {
		return BUFFER_SIZE * 2;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - size) * 2;
	}
	
	@Override
	public int getNumBits() {
		return 4;
	}
	
	@Override
	public void reset() {
		size = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size = other.getSize();
		for (int i=0; i < this.size; i++) {
			data[i] = (char)other.getValue(i);
		}
	}
	
	
}
