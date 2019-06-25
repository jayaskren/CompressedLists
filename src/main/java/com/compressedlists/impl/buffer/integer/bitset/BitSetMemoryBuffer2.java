package com.compressedlists.impl.buffer.integer.bitset;

import java.util.BitSet;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

public class BitSetMemoryBuffer2 implements IIntMemoryBuffer {
	
	private final BitSet bitset;
	private int count = 0;
	
	public BitSetMemoryBuffer2() {
		bitset = new BitSet(BUFFER_SIZE<<1);
	}

	@Override
	public int getSize() {
		return count;
	}

	@Override
	public int getSizeInBytes() {
		return BUFFER_SIZE/4;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - count)/4;
	}

	@Override
	public void addValue(int value) {
//		if ((value & 1) == 1) {
//			bitset.add(2*count);
//		} 
//		
//		if (((value >> 1) & 1)==1) {
//			bitset.add(2*count+1);
//		} 
		
		bitset.set(2*count, (value & 1) == 1);
		bitset.set(2*count+1, ((value >> 1) & 1)==1);
		count +=1;
	}

	@Override
	public void setValue(int pos, int value) {
//		if ((value & 1) == 1) {
//			bitset.add(2*pos);
//		} else {
//			bitset.remove(2*pos);
//		}
//		
//		if (((value >> 1) & 1)==1) {
//			bitset.add(2*pos+1);
//		} else {
//			bitset.remove(2*pos+1);
//		}
		
		if ((value & 1) == 1) {
			bitset.set(2*pos);
		}
		
		if (((value >> 1) & 1)==1) {
			bitset.set(2*pos+1);
		}
	}

	@Override
	public int getValue(int pos) {
		boolean val0 = bitset.get(2*pos);
		boolean val1 = bitset.get(2*pos+1);
		int returnVal;
		if (val0) {
			returnVal = 1;
		} else {
			returnVal = 0;
		}
		
		if (val1) {
			return  2 | returnVal;
		} else {
			return returnVal;
		}
		
	}
	
	boolean _getBit(int actualPos) {
		return bitset.get(actualPos);
	}

	@Override
	public int getNumBits() {
		return 1;
	}

	@Override
	public void reset() {
		count = 0;
	}

	@Override
	public void copy(IIntMemoryBuffer other) {
		this.count = other.getSize();
		for (int i=0; i < count; i++) {
			if ((other.getValue(i) & 1) == 1) {
				bitset.set(2*i);
			}
			
			if (((other.getValue(i) >> 1) & 1)==1) {
				bitset.set(2*i+1);
			}
		}
	}

}
