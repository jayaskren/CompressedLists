package com.compressedlists;

public interface IntList extends CompressedList {
	
	boolean addValue(int value);
	int getValue(int i);
	int calculateAverage();
	int getMax();
	int getMin();
	IntList add(int val);
	IntList add(IntList lst);
}
