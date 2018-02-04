package com.compressedlists.impl.buffer;

public interface IMemoryBuffer {
		
	public abstract int getSize();

	public abstract int getSizeInBytes();

	public abstract int getWaistedSizeInBytes();
	
	public void reset();

}