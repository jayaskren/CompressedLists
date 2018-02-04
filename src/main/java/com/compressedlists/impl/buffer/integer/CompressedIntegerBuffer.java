package com.compressedlists.impl.buffer.integer;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.IMemoryBuffer;

public class CompressedIntegerBuffer implements IMemoryBuffer {

	private int size;
	public final int[] compressedData;
	private int uncompressedByteSize = 0;
	
	public CompressedIntegerBuffer(int[] values) {
		size = values.length;
		
		uncompressedByteSize = values.length * 4;
		compressedData = IntegerCompressor.getInstance().compress(values);
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
		return new UncompressedIntegerBuffer(data);
	}
}
