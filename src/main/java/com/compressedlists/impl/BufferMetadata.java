package com.compressedlists.impl;

import com.compressedlists.CompressedList;
import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.jsoniter.annotation.JsonIgnore;

public abstract class BufferMetadata {
	
	
	@JsonIgnore
	int pos;
	
	int start;
	int length;

	public BufferMetadata(int pos) {
		this.pos = pos;
		
	}
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	@JsonIgnore
	public int getNumRecords() {
		IMemoryBuffer buf = getColumn().getBufferList().get(pos);
		return buf.getSize();
	}
	
	@JsonIgnore
	public abstract CompressedList getColumn();
	
}
