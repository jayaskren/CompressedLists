package com.compressedlists.impl;


import java.util.ArrayList;
import java.util.List;

import org.HdrHistogram.Histogram;

import com.compressedlists.DataType;
import com.compressedlists.StringList;

//import com.gs.collections.api.map.MutableMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2CharOpenHashMap;
//import com.gs.collections.impl.map.mutable.UnifiedMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public abstract class AbstractDictionaryStringList implements StringList {
	protected final int maxUniqueValues = (int) Math.pow(2,16)-1;
	protected final int maxSize = Integer.MAX_VALUE;
//	Histogram histogram = new Histogram(3600000000000L, 3);
	Object2CharOpenHashMap<String> uniqueValuesMap;
	protected final List<String> uniqueValues = new ArrayList<String>();
	protected final IntArrayList count;
	protected long currentSizeBytes;
	protected int size;
	protected long uniqueValuesNumChars = 0l;
	long totalTimeProcessed = 0l;
	long originalSize = 0l;
	public AbstractDictionaryStringList () {
		uniqueValuesMap = new Object2CharOpenHashMap<String>();
		count = new IntArrayList();
	}
	
	protected abstract void addIndex(int index);
	
	
	public void addValue(String val) {
		long begin = System.nanoTime();
		char uniqueValuesPosition;
		
		String uniqueString = val;
		
		if (hasMaxUniqueValues()) {
			uniqueString = "[Other]";
		} 
		
		if (uniqueValuesMap.containsKey(uniqueString)) {
			uniqueValuesPosition = uniqueValuesMap.getChar(uniqueString);
			count.set(uniqueValuesPosition, count.getInt(uniqueValuesPosition) + 1);
		} else {
			uniqueValuesPosition = (char)uniqueValues.size();
			uniqueValues.add(uniqueString);
			uniqueValuesMap.put(uniqueString, uniqueValuesPosition);
			uniqueValuesNumChars += uniqueString.length();
			count.add(1);
		}
		size++;
		addIndex(uniqueValuesPosition);
		
		long timeProcessed = System.nanoTime() - begin;
		totalTimeProcessed += timeProcessed;
		originalSize+=val.length(); // 1 byte per character for UTF-8
//		histogram.recordValue(timeProcessed);
	}
	
	/**This list can only handle 2^16 unique values*/
	public boolean hasMaxUniqueValues() {
		return getUniqueValuesSize() >= maxUniqueValues;
	}
	
	public String getValue(int i) {
		int index = getIndexValue(i);
		return uniqueValues.get(index);
	}
	
	public abstract int getIndexValue(int i);
	
	public int getMaxSize() {
		return maxSize;
	}

	public int getUniqueValuesSize() {
		return uniqueValues.size();
	}

	public List<String> getUniqueValues() {
		return uniqueValues;
	}

	public int getUniqueValueCount(int i) {
		return count.get(i);
	}

	public String getUniqueValueCountAsString(int i) {
		return String.valueOf(count.get(i));
	}

	public int getMaxUniqueValues() {
		return maxUniqueValues;
	}
	
	public long getUniqueValuesSizeInBytes() {
		return uniqueValuesNumChars * 2;
	}
	
	public String getValueDisplay(int i) {
		return getValue(i);
	}
	
	public DataType getDataType() {
		return DataType.TEXT;
	}

	public int getSize() {
		return size;
	}
	
	public Histogram getHistogram() {
		return null;//histogram;
	}
	
	@Override
	public long getTimeProcessed() {
		return totalTimeProcessed/1000000;
	}
	
	public long getOriginalSizeInBytes() {
		return originalSize;
	}
}



