package com.compressedlists;

import org.HdrHistogram.Histogram;

public interface CompressedList {

	public DataType getDataType();

	public int getSize();

	void addValue(String str);

	public String getValueDisplay(int i);

	public int getMaxSize();

	public long getSizeInBytes();
	
	public Histogram getHistogram();
	
	public boolean hasMaxUniqueValues();
	
	public long getTimeProcessed();

}