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
	
	public int readFromFile(RandomAccessFile file, CompressionType compression, int numBytes, int numRecords) throws IOException;

}