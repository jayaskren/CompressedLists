package com.compressedlists.impl.buffer.integer.intset;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import gnu.trove.set.hash.TIntHashSet;

public class IntSetMemoryBuffer1 implements IIntMemoryBuffer {
	private TIntHashSet hashSet;
	private int count = 0;
	
	public IntSetMemoryBuffer1() {
		hashSet = new TIntHashSet(BUFFER_SIZE);
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
		if (value==1) {
			hashSet.add(count);
		} 

		count ++;
	}

	@Override
	public void setValue(int pos, int value) {
		if (value==1) {
			hashSet.add(pos);
		} else { 
			hashSet.remove(pos);
		}
	}

	@Override
	public int getValue(int pos) {
		boolean val = hashSet.contains(pos);
		
		int returnVal;
		if (val) {
			returnVal = 1;
		} else {
			returnVal = 0;
		}
		return returnVal;
	}

	@Override
	public int getNumBits() {
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
			setValue(i, other.getValue(i));
		}
	}
}
