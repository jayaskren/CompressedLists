package com.compressedlists.impl;

import com.jsoniter.annotation.JsonIgnore;

public abstract class BufferMetadata {
	
	
	@JsonIgnore
	int pos;
	
	int start;
	int numRows;
	
	public BufferMetadata(int pos, int start, int numRows) {
		this(pos);
		this.start = start;
		this.numRows = numRows;
	}

	public BufferMetadata(int pos) {
		this.pos = pos;
		
	}
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	
	
//	@JsonIgnore
//	public int getNumRecords() {
//		IMemoryBuffer buf = getColumn().getBufferList().get(pos);
//		return buf.getSize();
//	}
	
//	@JsonIgnore
//	public abstract CompressedList getColumn();
	
}
