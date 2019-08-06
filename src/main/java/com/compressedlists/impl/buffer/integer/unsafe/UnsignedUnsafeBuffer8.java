package com.compressedlists.impl.buffer.integer.unsafe;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import sun.misc.Unsafe;

public class UnsignedUnsafeBuffer8 extends AbstractUnsafeBuffer {

	public UnsignedUnsafeBuffer8() {
		super(1);
	}

	@Override
	protected void putValue(final Unsafe unsafe, int pos, int value) {
		unsafe.putByte(data, address + pos * numBytesPerRow, (byte)(0b11111111 & value));
	}

	@Override
	protected int getValue(final Unsafe unsafe, int pos) {
		return unsafe.getByte(data, address + (pos)*numBytesPerRow) & 0b11111111;
	}
	
	@Override
	public int getLogOfBitsPerRow() {
		return 3;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size=other.getSize();
		for (int i=0; i<size; i++) {
			unsafe.putByte(data, address + i * numBytesPerRow, (byte)(0b11111111 & other.getValue(i)));
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
