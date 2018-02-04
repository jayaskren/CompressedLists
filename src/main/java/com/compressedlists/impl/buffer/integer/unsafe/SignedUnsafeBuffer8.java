package com.compressedlists.impl.buffer.integer.unsafe;

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
	public int getNumBits() {
		return 3;
	}
}
