package com.compressedlists.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.BitUtil;
import com.compressedlists.impl.buffer.BufferCachingFactory;
import com.compressedlists.impl.buffer.BufferType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.compressedlists.impl.buffer.MemorySizeInfo;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class StringListImpl extends AbstractDictionaryStringList {
	BufferCachingFactory factory;
	protected BufferType bufferType = BufferType.Array;
	List<IIntMemoryBuffer> bufferList;
	protected int currentLogNumBits = 0;
	protected int lastBufferIndex;
	protected int lastBufferSize;
	protected IIntMemoryBuffer lastMemoryBuffer;
	
	public StringListImpl() {
		super();
		bufferList = new ArrayList<IIntMemoryBuffer>();
		factory = new BufferCachingFactory();
		lastMemoryBuffer = createNewBuffer(0);
	}
	
	public StringListImpl(List<String> uniqueValues, List<StringBufferMetadata> mdList) {
		super(uniqueValues);
		this.bufferList = new ArrayList<IIntMemoryBuffer>();
		factory = new BufferCachingFactory();
		for (StringBufferMetadata md : mdList ) {
			createNewBuffer(BitUtil.log2nlz(md.getNumBitsPerRow()));
		}
		this.count.ensureCapacity(uniqueValues.size());
		for (int i=0; i < uniqueValues.size(); i++) {
			this.count.add(0);
		}
		lastMemoryBuffer = null;
	}
	
	protected void addIndex(int index) {
		int logNumBits;
		
		logNumBits = BitUtil.numBits(index);
		
		if (lastBufferSize >=  IIntMemoryBuffer.BUFFER_SIZE) { 
			// We need a new buffer. Create new one and set indexes appropriately
			lastMemoryBuffer = createNewBuffer(logNumBits);	
		} else if (logNumBits > currentLogNumBits) {
			lastMemoryBuffer = createAndCopyBuffer(logNumBits);
		} 

		lastMemoryBuffer.addValue(index);
		lastBufferSize++;
	}

	protected IIntMemoryBuffer createAndCopyBuffer(int logNumBits) {
		IIntMemoryBuffer buffer;
		// Go to next bigger bit size.  Copy values from current buffer
		// to new array.  Set indexes appropriately
		currentLogNumBits = Math.max(logNumBits, currentLogNumBits);
		IIntMemoryBuffer oldBuffer = bufferList.remove(bufferList.size()-1);
		buffer = factory.tradeForNewBufferAndCopy(currentLogNumBits, oldBuffer);
		bufferList.add(buffer);
		lastBufferIndex = bufferList.size() - 1;
		lastBufferSize = buffer.getSize();
		return buffer;
	}

	protected IIntMemoryBuffer createNewBuffer(int logNumBits) {
		IIntMemoryBuffer buffer;
		currentLogNumBits = Math.max(logNumBits, currentLogNumBits);
		buffer = factory.getNewBuffer(currentLogNumBits);
		bufferList.add(buffer);
		lastBufferIndex = bufferList.size() - 1;
		lastBufferSize = 0;
		return buffer;
	}
	
	int getIndexValue(List<byte[]> currentList, int index, int bitIndex, int listIndex) {
		byte[] currentArray = currentList.get(listIndex);
		return currentArray[bitIndex];
	}
	
	@Override
	public int getBufferSize(int i) {
		return bufferList.get(i).getSize();
	}
	
	public void setValue(int i, String val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIndexValue(int i) {
		int bufferIndex = i >> IIntMemoryBuffer.NUM_BITS;  // Same as i /  16
		int bitIndex = i & IIntMemoryBuffer.BUFFER_SIZE_MODULO_MASK; // Same i % 
		
		return bufferList.get(bufferIndex).getValue(bitIndex);
	}

	@Override
	public MemorySizeInfo getIndexSizeInBytes() {
		long sizeInBytes = 0;
		long waistedSizeInBytes = 0;
		for (IMemoryBuffer buffer : bufferList) {
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

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression, int bufferIndex, BufferMetadata metadata) throws IOException {
		return bufferList.get(bufferIndex).writeData(file, compression);
	}

	@Override
	public int readData(RandomAccessFile file, CompressionType compression, int index, int numBytes, int numRecords, BufferMetadata metadata) throws IOException {
		IIntMemoryBuffer buffer = bufferList.get(index);
		this.size+=numRecords;
//		metadata.
		this.currentLogNumBits = BitUtil.log2nlz((numBytes/numRecords)*8); // TODO double check this
		int bytesRead = buffer.readFromFile(file, compression, numBytes, numRecords);
		
//		for (int i=0; i < buffer.getSize() ; i++) {
//			int uniqueValuesIndex = buffer.getValue(i);
//			count.set(uniqueValuesIndex, count.getInt(uniqueValuesIndex) + 1); // TODO is there a faster way to do this?
//		}
		return bytesRead;
	}

	@Override
	public List<? extends IMemoryBuffer> getBufferList() {
		return bufferList;
	}
	
	
}
