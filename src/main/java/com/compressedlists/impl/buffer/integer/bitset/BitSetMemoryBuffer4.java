package com.compressedlists.impl.buffer.integer.bitset;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class BitSetMemoryBuffer4 implements IIntMemoryBuffer {

	private final BitSet bitset;
	private int count = 0;
	
	public BitSetMemoryBuffer4() {
		bitset = new BitSet(BUFFER_SIZE<<1);
	}
	
	@Override
	public int getSize() {
		return count;
	}

	@Override
	public int getSizeInBytes() {
		return BUFFER_SIZE/2;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - count)/2;
	}

	@Override
	public void addValue(int value) {
		if ((value & 1) == 1) {
			bitset.set(4*count);
		} 
		
		if (((value >> 1) & 1) == 1) {
			bitset.set(4*count+1);
		} 
		
		if (((value >> 2) & 1) == 1) {
			bitset.set(4*count+2);
		} 
		
		if (((value >> 3) & 1) == 1) {
			bitset.set(4*count+3);
		}
		
//		bitset.set(4*count, (value & 1) == 1);
//		bitset.set(4*count+1, ((value >> 1) & 1) == 1);
//		bitset.set(4*count+2, ((value >> 2) & 1) == 1);
//		bitset.set(4*count+3, ((value >> 3) & 1) == 1);
		count +=1;
	}

	@Override
	public void setValue(int pos, int value) {
//		if ((value & 1) == 1) {
//			bitset.add(4*pos);
//		} else {
//			bitset.remove(4*pos);
//		}
//		
//		if (((value >> 1) & 1) == 1) {
//			bitset.add(4*pos+1);
//		} else {
//			bitset.remove(4*pos+1);
//		}
//		
//		if (((value >> 2) & 1) == 1) {
//			bitset.add(4*pos+2);
//		} else {
//			bitset.remove(4*pos+2);
//		}
//		
//		if (((value >> 3) & 1) == 1) {
//			bitset.add(4*pos+3);
//		} else {
//			bitset.remove(4*pos+3);
//		}
		
		bitset.set(4*pos, (value & 1) == 1);
		bitset.set(4*pos+1, ((value >> 1) & 1) == 1);
		bitset.set(4*pos+2, ((value >> 2) & 1) == 1);
		bitset.set(4*pos+3, ((value >> 3) & 1) == 1);
	}

	@Override
	public int getValue(int pos) {
		boolean val0 = bitset.get(4*pos);
		boolean val1 = bitset.get(4*pos+1);
		boolean val2 = bitset.get(4*pos+2);
		boolean val3 = bitset.get(4*pos+3);
		int returnVal;
		if (val0) {
			returnVal = 1;
		} else {
			returnVal = 0;
		}
		
		if (val1) {
			returnVal = 2 | returnVal;
		} 
		
		if (val2) {
			returnVal = 4 | returnVal;
		} 
		
		if (val3) {
			returnVal = 8 | returnVal;
		} 
		
		return returnVal;
	}

	boolean _getBit(int actualPos) {
		return bitset.get(actualPos);
	}

	@Override
	public int getLogOfBitsPerRow() {
		return 2;
	}
	
	@Override
	public void reset() {
		count = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.count = other.getSize();
		for (int i=0; i < count; i++) {
			bitset.set(4*i, (other.getValue(i) & 1) == 1);
			bitset.set(4*i+1, ((other.getValue(i) >> 1) & 1) == 1);
			bitset.set(4*i+2, ((other.getValue(i) >> 2) & 1) == 1);
			bitset.set(4*i+3, ((other.getValue(i) >> 3) & 1) == 1);
		}
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readFromFile(RandomAccessFile file, CompressionType compression, int numBytes, int numRecords) throws IOException {
		return 0;
	}
}
