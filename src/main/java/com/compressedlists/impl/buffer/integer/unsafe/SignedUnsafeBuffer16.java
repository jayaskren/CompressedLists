package com.compressedlists.impl.buffer.integer.unsafe;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import sun.misc.Unsafe;

public class SignedUnsafeBuffer16 extends AbstractUnsafeBuffer {

	public SignedUnsafeBuffer16() {
		super(2);
	}

	@Override
	protected void putValue(final Unsafe unsafe, int pos, int value) {
		unsafe.putShort(data, address + pos * numBytesPerRow, (short)value);
	}

	@Override
	protected int getValue(final Unsafe unsafe, int pos) {
		return unsafe.getChar(data, address + pos * numBytesPerRow);
	}
	
	@Override
	public int getLogOfBitsPerRow() {
		return 4;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size=other.getSize();
		for (int i=0; i<size; i++) {
			unsafe.putShort(data, address + i * numBytesPerRow, (short)other.getValue(i));
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
