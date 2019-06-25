package com.compressedlists;

import org.HdrHistogram.Histogram;

import com.compressedlists.impl.buffer.MemorySizeInfo;


public interface TextList extends CompressedList {

	void addValue(String str);
	void setValue(int i, String val);
	String getValue(int i);
	MemorySizeInfo getIndexSizeInBytes();
	long getUniqueValuesSizeInBytes();
	int getUniqueValuesSize();
	Histogram getHistogram();
	char[] getUniqueCharacters();
	String[] getUniqueNGrams(int n);
}
