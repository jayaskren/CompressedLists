package com.compressedlists.impl.buffer.integer.unsafe;

import sun.misc.Unsafe;

public class UnsignedUnsafeBuffer16 extends AbstractUnsafeBuffer {

	public UnsignedUnsafeBuffer16() {
		super(2);
	}

	@Override
	protected void putValue(final Unsafe unsafe, int pos, int value) {
		unsafe.putChar(data, address + pos * numBytesPerRow, (char)value);
	}

	@Override
	protected int getValue(final Unsafe unsafe, int pos) {
		return (char)unsafe.getChar(data, address + pos * numBytesPerRow);
	}
	
	@Override
	public int getNumBits() {
		return 4;
	}
}
