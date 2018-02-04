package com.compressedlists.impl.buffer.integer.unsafe;

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
		return (short)unsafe.getShort(data, address + pos * numBytesPerRow);
	}
	
	@Override
	public int getNumBits() {
		return 4;
	}
}
