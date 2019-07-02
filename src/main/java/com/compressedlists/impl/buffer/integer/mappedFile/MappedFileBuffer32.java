package com.compressedlists.impl.buffer.integer.mappedFile;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class MappedFileBuffer32 implements IIntMemoryBuffer {

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSizeInBytes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWaistedSizeInBytes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addValue(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(int pos, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getValue(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLogOfBitsPerRow() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void readFromFile(RandomAccessFile file, CompressionType compression, int numRecords, int numBytes)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

}
