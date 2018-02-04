package com.compressedlists.impl.buffer.integer;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class UncompressedIntegerBuffer implements IIntMemoryBuffer{
	protected int[] data;
	protected int size;
	private int sizeInBytes = 0;
	
	public UncompressedIntegerBuffer() {
		data = new int[BUFFER_SIZE];
		size = 0;
		sizeInBytes = 0;
	}
	
	public UncompressedIntegerBuffer(int[] data) {
		this.data = data;
		this.size = data.length;
		this.sizeInBytes = data.length * 4;
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSizeInBytes() {
		return sizeInBytes;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return 0;
	}

	@Override
	public void reset() {
		size = 0;
	}

	@Override
	public void addValue(int value) {
		if (value == 32767) {
			System.out.println("Test");
		}
		data[size] = value;
		sizeInBytes += 4;
		size ++;
	}

	@Override
	public void setValue(int pos, int value) {
		
	}

	@Override
	public int getValue(int pos) {
		return data[pos];
	}

	@Override
	public int getNumBits() {
		return 0;
	}

	public CompressedIntegerBuffer compressToBuffer() {
		return new CompressedIntegerBuffer(data);
	}

}
