package com.compressedlists.impl.buffer;


public interface IBufferFactory {

	IIntMemoryBuffer createBuffer(int numBits);
}
