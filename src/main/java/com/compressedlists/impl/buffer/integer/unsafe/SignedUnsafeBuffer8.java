package com.compressedlists.impl.buffer.integer.unsafe;

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
	public int getNumBits() {
		return 3;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size=other.getSize();
		for (int i=0; i<size; i++) {
			unsafe.putByte(data, address + i * numBytesPerRow, (byte)other.getValue(i));
		}
	}
}
