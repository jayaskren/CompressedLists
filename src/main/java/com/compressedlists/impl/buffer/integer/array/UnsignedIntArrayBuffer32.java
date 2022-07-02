package com.compressedlists.impl.buffer.integer.array;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.BitUtil;
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
	public int getLogOfBitsPerRow() {
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

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) throws IOException {
		byte[] bytes = BitUtil.intToBytes(this.data, this.size);
		
		switch (compression) {
		case GZIP:
			
			break;
		case LZ4:
			
			break;
		case SNAPPY:
			
			break;
		case ZSTD:
			
			break;
		default:
			file.write(bytes, 0, getSize() * 4);
			return getSize() * 4;
		}
		return 0;
	}
	
	@Override
	public int readFromFile(RandomAccessFile file, CompressionType compression, int numBytes, int numRecords) throws IOException {
		byte[] temp = new byte[numBytes];
		int numRead = file.read(temp, 0, numBytes);
		size = numRecords;
		if (numRecords < data.length) {
			Arrays.fill(data, numRecords, data.length, 0);
		}
		BitUtil.bytesToInts(temp, data, numRecords);
		return numRead;
	}
}
