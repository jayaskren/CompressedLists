package com.compressedlists.impl.buffer.integer;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.IMemoryBuffer;

public class CompressedIntegerBuffer implements IMemoryBuffer {

	private int size;
	public final int[] compressedData;
	private int uncompressedByteSize = 0;
	private int min;
	private int max;
	
	public CompressedIntegerBuffer(int[] values) {
		size = values.length;
		
		uncompressedByteSize = values.length * 4;
		compressedData = IntegerCompressor.getInstance().compress(values);
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSizeInBytes() {
		return compressedData.length;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return 0;
	}


	@Override
	public void reset() {
		size = 0;
	}

	public UncompressedIntegerBuffer uncompress() {
		int[] data = new int[IIntMemoryBuffer.BUFFER_SIZE];
		IntegerCompressor.getInstance().uncompress(compressedData, data);
		return new UncompressedIntegerBuffer(data, min, max);
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void readFromFile(RandomAccessFile file, CompressionType compression, int numRecords, int numBytes)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
}
