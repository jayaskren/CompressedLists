package com.compressedlists.impl.buffer.integer;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class UncompressedIntegerBuffer implements IIntMemoryBuffer{
	protected int[] data;
	protected int size; 
	private int sizeInBytes = 0;
	private int min = 0;
	private int max = 0;
	
	public UncompressedIntegerBuffer() {
		data = new int[BUFFER_SIZE];
		size = 0;
		sizeInBytes = 0;
	}
	
	public UncompressedIntegerBuffer(int[] data, int min, int max) {
		this.data = data;
		this.size = data.length;
		this.sizeInBytes = data.length * 4;
		this.min = min;
		this.max = max;
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
		data[size] = value;
		sizeInBytes += 4;
		size ++;
		if (min < value) {
			min = value;
		}
		if (max < value) {
			max = value;
		}
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

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public CompressedIntegerBuffer compressToBuffer() {
		return new CompressedIntegerBuffer(data);
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size = other.getSize();
		sizeInBytes = 4 * size;
		for (int i=0; i< other.getSize(); i++) {
			data[i] = other.getValue(i);
		}
	}

}
