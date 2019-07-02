package com.compressedlists.impl.buffer.integer.bytebuffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class ByteBufferMemoryBuffer32 implements IIntMemoryBuffer {

	protected IntBuffer buffer;
	protected int size;
	
	public ByteBufferMemoryBuffer32 (boolean useDirect) {
		ByteBuffer byteBuffer;
		if (useDirect) {
			byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE*4);
		} else {
			byteBuffer = ByteBuffer.allocate(BUFFER_SIZE*4);
		}
		buffer = byteBuffer.asIntBuffer();
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
	public void addValue(int value) {
		buffer.put(value);
		size ++;
	}

	@Override
	public void setValue(int pos, int value) {
		buffer.put(pos, value);
	}

	@Override
	public int getValue(int pos) {
		return buffer.get(pos);
	}

	@Override
	public int getLogOfBitsPerRow() {
		
		return 5;
	}

	@Override
	public void reset() {
		buffer.clear();
		size = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		int size = other.getSize();
		for (int i=0; i < size ; i++) {
			buffer.put(i, other.getValue(i));
		}
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void readFromFile(RandomAccessFile file, CompressionType compression, int numRecords, int numBytes) throws IOException {
		
	}
}
