package com.compressedlists.impl.buffer.integer.unsafe;

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
	public int getNumBits() {
		return 5;
	}
}
