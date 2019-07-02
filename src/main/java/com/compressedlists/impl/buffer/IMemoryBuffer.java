package com.compressedlists.impl.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.compressedlists.CompressionType;

public interface IMemoryBuffer {
		
	public abstract int getSize();

	public abstract int getSizeInBytes();

	public abstract int getWaistedSizeInBytes();
	
	public void reset();
	
	public int writeData(RandomAccessFile file, CompressionType compression) throws IOException;
	
	public void readFromFile(RandomAccessFile file, CompressionType compression, int numRecords, int numBytes) throws IOException;

}