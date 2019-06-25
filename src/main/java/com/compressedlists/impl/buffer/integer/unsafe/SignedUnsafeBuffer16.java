package com.compressedlists.impl.buffer.integer.unsafe;

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
	public int getNumBits() {
		return 4;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.size=other.getSize();
		for (int i=0; i<size; i++) {
			unsafe.putShort(data, address + i * numBytesPerRow, (short)other.getValue(i));
		}
	}
}
