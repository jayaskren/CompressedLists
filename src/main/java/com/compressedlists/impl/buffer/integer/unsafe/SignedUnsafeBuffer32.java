package com.compressedlists.impl.buffer.integer.unsafe;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import sun.misc.Unsafe;

public class SignedUnsafeBuffer32 extends AbstractUnsafeBuffer {

	public SignedUnsafeBuffer32() {
		super(4);
	}

	@Override
	protected void putValue(final Unsafe unsafe, int pos, int value) {
		unsafe.putInt(data, address + pos * numBytesPerRow, value);
	}

	@Override
	protected int getValue(final Unsafe unsafe, int pos) {
		return unsafe.getInt(data, address + (pos)*numBytesPerRow);
	}
	
	@Override
	public int getLogOfBitsPerRow() {
		return 5;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size=other.getSize();
		for (int i=0; i<size; i++) {
			unsafe.putInt(data, address + i * numBytesPerRow, other.getValue(i));
		}
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readFromFile(RandomAccessFile file, CompressionType compression, int numBytes, int numRecords)
			throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}
