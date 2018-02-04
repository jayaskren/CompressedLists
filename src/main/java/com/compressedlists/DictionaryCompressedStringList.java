package com.compressedlists;

import java.util.List;

public interface DictionaryCompressedStringList extends CompressedStringList {

	int getUniqueValuesSize();
	List<String> getUniqueValues();
	int getUniqueValueCount(int i);
	String getUniqueValueCountAsString(int i);
	int getMaxUniqueValues();
}
