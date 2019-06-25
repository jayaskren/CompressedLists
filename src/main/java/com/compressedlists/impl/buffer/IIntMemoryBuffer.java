package com.compressedlists.impl.buffer;

public interface IIntMemoryBuffer extends IMemoryBuffer {

	final int NUM_BITS = 16;
	final int BUFFER_SIZE = (int) Math.pow(2, NUM_BITS);
	final int BUFFER_SIZE_MODULO_MASK = BUFFER_SIZE -1;
	void addValue(int value);
	void setValue(int pos, int value);
	int getValue(int pos);
	int getNumBits();
	void copy(IIntMemoryBuffer other);
}
