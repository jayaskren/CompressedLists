package com.compressedlists.impl;

import java.util.ArrayList;
import java.util.List;

import com.compressedlists.impl.buffer.BitUtil;
import com.compressedlists.impl.buffer.BufferCachingFactory;
import com.compressedlists.impl.buffer.BufferType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.compressedlists.impl.buffer.MemorySizeInfo;

public class StringListImpl extends AbstractDictionaryStringList {
	protected final BufferCachingFactory factory;
	protected final BufferType bufferType = BufferType.Array;
	protected final List<IIntMemoryBuffer> indexList;
	protected int currentNumBits = 0;
	protected int lastBufferIndex;
	protected int lastBufferSize;
	protected IIntMemoryBuffer lastMemoryBuffer;
	
	public StringListImpl() {
		super();
		indexList = new ArrayList<IIntMemoryBuffer>();
		factory = new BufferCachingFactory();
		lastMemoryBuffer = createNewBuffer(0);
		
	}
	
	protected void addIndex(int index) {
		int logNumBits;
		
		logNumBits = BitUtil.numBits(index);
		
		if (lastBufferSize >=  IIntMemoryBuffer.BUFFER_SIZE) { 
			// We need a new buffer. Create new one and set indexes appropriately
			lastMemoryBuffer = createNewBuffer(logNumBits);	
		} else if (logNumBits > currentNumBits) {
			lastMemoryBuffer = createAndCopyBuffer(logNumBits);
		} 

		lastMemoryBuffer.addValue(index);
		lastBufferSize++;
	}

	protected IIntMemoryBuffer createAndCopyBuffer(int logNumBits) {
		IIntMemoryBuffer buffer;
		// Go to next bigger bit size.  Copy values from current buffer
		// to new array.  Set indexes appropriately
		currentNumBits = Math.max(logNumBits, currentNumBits);
		IIntMemoryBuffer oldBuffer = indexList.remove(indexList.size()-1);
		buffer = factory.tradeForNewBufferAndCopy(currentNumBits, oldBuffer);
		indexList.add(buffer);
		lastBufferIndex = indexList.size() - 1;
		lastBufferSize = buffer.getSize();
		return buffer;
	}

	protected IIntMemoryBuffer createNewBuffer(int logNumBits) {
		IIntMemoryBuffer buffer;
		currentNumBits = Math.max(logNumBits, currentNumBits);
		buffer = factory.getNewBuffer(currentNumBits);
		indexList.add(buffer);
		lastBufferIndex = indexList.size() - 1;
		lastBufferSize = 0;
		return buffer;
	}
	
	int getIndexValue(List<byte[]> currentList, int index, int bitIndex, int listIndex) {
		byte[] currentArray = currentList.get(listIndex);
		return currentArray[bitIndex];
	}
	
	public void setValue(int i, String val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIndexValue(int i) {
		int bufferIndex = i >> IIntMemoryBuffer.NUM_BITS;
		int bitIndex = i & IIntMemoryBuffer.BUFFER_SIZE_MODULO_MASK; 
		
		return indexList.get(bufferIndex).getValue(bitIndex);
	}

	@Override
	public MemorySizeInfo getIndexSizeInBytes() {
		long sizeInBytes = 0;
		long waistedSizeInBytes = 0;
		for(IMemoryBuffer buffer : indexList) {
			sizeInBytes += buffer.getSizeInBytes();
			waistedSizeInBytes += buffer.getWaistedSizeInBytes();
		}
		MemorySizeInfo info = new MemorySizeInfo();
		info.sizeInBytes = sizeInBytes;
		info.waistedSizeInBytes = waistedSizeInBytes;
		return info;
	}

	@Override
	public long getSizeInBytes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char[] getUniqueCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getUniqueNGrams(int n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
