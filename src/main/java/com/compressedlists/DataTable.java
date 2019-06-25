package com.compressedlists;

public class DataTable {
	private CompressedList[] data;
	private String[] headerNames;
	private String[] uniqueNames;

	public DataTable(CompressedList[] data, String[] headerNames, String[] uniqueNames) {
		this.data = data;
		this.headerNames = headerNames;
		this.uniqueNames = uniqueNames;
	}

	public CompressedList[] getData() {
		return data;
	}

	public String[] getHeaderNames() {
		return headerNames;
	}

	public String[] getUniqueNames() {
		return uniqueNames;
	}
	
	
}
