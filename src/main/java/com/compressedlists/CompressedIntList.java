package com.compressedlists;

public interface CompressedIntList extends CompressedList {
	
	boolean addValue(int value);
	int getValue(int i);
	int calculateAverage();
	CompressedIntList add(int val);
	CompressedIntList add(CompressedIntList lst);
}
