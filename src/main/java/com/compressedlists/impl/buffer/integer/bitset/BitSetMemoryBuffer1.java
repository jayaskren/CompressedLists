package com.compressedlists.impl.buffer.integer.bitset;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;

import org.roaringbitmap.RoaringBitmap;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class BitSetMemoryBuffer1 implements IIntMemoryBuffer {

	private final BitSet bitset;
	private int count = 0;
	
	public BitSetMemoryBuffer1() {
		bitset = new BitSet(/*BUFFER_SIZE*/);
	}
	
	@Override
	public int getSize() {
		return count;
	}

	@Override
	public int getSizeInBytes() {
		return BUFFER_SIZE/8;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - count)/8;
	}

	@Override
	public void addValue(int value) {
//		if (value==1) {
//			bitset.add(count);
//		} 

		if (value == 1) {
			bitset.set(count);
		}
		count ++;
	}

	@Override
	public void setValue(int pos, int value) {
//		if (value==1) {
//			bitset.add(pos);
//		} else { 
//			bitset.remove(pos);
//		}
		
		bitset.set(pos, value==1);
	}

	@Override
	public int getValue(int pos) {
		boolean val = bitset.get(pos);
		
		int returnVal;
		if (val) {
			returnVal = 1;
		} else {
			returnVal = 0;
		}
		return returnVal;
	}

	@Override
	public int getLogOfBitsPerRow() {
		return 0;
	}

	@Override
	public void reset() {
		count = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.count = other.getSize();
		for (int i=0; i < count; i++) {
			bitset.set(i, other.getValue(i)==1);
		}
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void readFromFile(RandomAccessFile file, CompressionType compression, int numRecords, int numBytes) throws IOException {
		
	}
}
