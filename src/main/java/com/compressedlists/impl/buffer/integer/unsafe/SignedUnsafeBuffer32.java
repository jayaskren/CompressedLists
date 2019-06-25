package com.compressedlists.impl.buffer.integer.unsafe;

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
	public int getNumBits() {
		return 5;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size=other.getSize();
		for (int i=0; i<size; i++) {
			unsafe.putInt(data, address + i * numBytesPerRow, other.getValue(i));
		}
	}
}
