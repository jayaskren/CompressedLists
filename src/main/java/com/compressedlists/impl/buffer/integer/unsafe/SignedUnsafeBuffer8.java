package com.compressedlists.impl.buffer.integer.unsafe;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import sun.misc.Unsafe;

public class SignedUnsafeBuffer8 extends AbstractUnsafeBuffer {

	public SignedUnsafeBuffer8() {
		super(1);
	}

	@Override
	protected void putValue(final Unsafe unsafe, int pos, int value) {
		unsafe.putByte(data, address + pos * numBytesPerRow, (byte)value);
	}

	@Override
	protected int getValue(final Unsafe unsafe, int pos) {
		return unsafe.getByte(data, address + (pos)*numBytesPerRow);
	}
	
	@Override
	public int getLogOfBitsPerRow() {
		return 3;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size=other.getSize();
		for (int i=0; i<size; i++) {
			unsafe.putByte(data, address + i * numBytesPerRow, (byte)other.getValue(i));
		}
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
