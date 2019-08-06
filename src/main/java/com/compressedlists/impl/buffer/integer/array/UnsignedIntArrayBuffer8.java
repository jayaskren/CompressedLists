package com.compressedlists.impl.buffer.integer.array;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.BitUtil;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;


public class UnsignedIntArrayBuffer8 implements IIntMemoryBuffer {

	protected byte[] data;
	protected int size;
	
	public UnsignedIntArrayBuffer8 () {
		data = new byte[BUFFER_SIZE];
	}
	
	@Override
	public void addValue(int value) {
		data[size++] = (byte)(0b11111111 & value);
	}
	
	@Override
	public void setValue(int pos, int value) {
		
		data[pos] = (byte)(0b11111111 & value);
	}

	@Override
	public int getValue(int pos) {
		return (int)( data[pos] & 0b11111111);
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
		return (BUFFER_SIZE - size);
	}

	@Override
	public int getLogOfBitsPerRow() {
		return 3;
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

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) throws IOException {
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
			file.write(data, 0, getSize());
			return getSize();
		}
		return 0;
	}

	@Override
	public int readFromFile(RandomAccessFile file, CompressionType compression, int numBytes, int numRecords) throws IOException {
		size = numRecords;
		if (numRecords < data.length) {
			Arrays.fill(data, numRecords, data.length, (byte)0);
		}
		int numBytesRead = file.read(data, 0, numRecords);
		this.size = numRecords;
		return numBytesRead;
	}
	
	
}
