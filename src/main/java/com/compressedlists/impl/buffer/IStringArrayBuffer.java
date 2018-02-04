package com.compressedlists.impl.buffer;

public interface IStringArrayBuffer extends IMemoryBuffer {

	final int NUM_BITS = 15;
	final int BUFFER_SIZE = (int) Math.pow(2, NUM_BITS);
	final int BUFFER_SIZE_MODULO_MASK = BUFFER_SIZE -1;
	
	
	void addValue(String value);
	void setValue(int pos, String value);
	String getValue(int pos);
	int getSize();
	int getSizeInBytes();
	int getWaistedSizeInBytes();

}
