package com.compressedlists.impl.buffer.integer.unsafe;

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
	public int getNumBits() {
		return 3;
	}
}
