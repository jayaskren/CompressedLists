package com.compressedlists;

import java.util.List;

public interface StringList extends TextList {

	int getUniqueValuesSize();
	List<String> getUniqueValues();
	int getUniqueValueCount(int i);
	String getUniqueValueCountAsString(int i);
	int getMaxUniqueValues();
}
