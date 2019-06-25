package com.compressedlists.impl.buffer;

import java.util.ArrayDeque;
import java.util.Deque;

public class BufferCachingFactory {
	
	protected ArrayBufferFactory factory;
	Deque<IIntMemoryBuffer>[] buffers;
	
	public BufferCachingFactory() {
		factory = new ArrayBufferFactory();
		buffers = new Deque[6];
		for (int i=0; i<buffers.length; i++) {
			buffers[i] = new ArrayDeque<IIntMemoryBuffer>();
		}
	}
	
	public IIntMemoryBuffer getNewBuffer(int numBits) {
		IIntMemoryBuffer buffer = buffers[numBits].poll();
		if (buffer == null) {
			return factory.createBuffer(numBits);
		}
		return buffer;
	}
	
	public IIntMemoryBuffer tradeForNewBufferAndCopy(int numBits, IIntMemoryBuffer bufferToCopy) {
		IIntMemoryBuffer buffer = getNewBuffer(numBits);
		buffer.copy(bufferToCopy);
		returnBuffer(bufferToCopy.getNumBits(), bufferToCopy);
		return buffer;
	}
	
//	public void copyBuffer(IIntMemoryBuffer oldBuffer, IIntMemoryBuffer newBuffer) {
//		for(int i=0; i<oldBuffer.getSize(); i++) {
//			newBuffer.addValue(oldBuffer.getValue(i));
//		}
//	}
	
	public void returnBuffer(int numBits, IIntMemoryBuffer bufferToCopy) {
		buffers[numBits].offer(bufferToCopy);
		bufferToCopy.reset();
	}
}
