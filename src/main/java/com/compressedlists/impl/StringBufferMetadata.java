package com.compressedlists.impl;

import com.compressedlists.CompressedList;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.jsoniter.annotation.JsonIgnore;

public class StringBufferMetadata extends BufferMetadata {
	
	@JsonIgnore
	StringListImpl column;
	
	public StringBufferMetadata(StringListImpl column, int pos) {
		super(pos);
		this.column = column;
	}
	
	public int getNumBitsPerRow() {
		IMemoryBuffer buf = column.getBufferList().get(pos);
		if (column.getUniqueValuesSize() >1) {
			if (buf instanceof IIntMemoryBuffer) {
				return (int)Math.pow(2, ((IIntMemoryBuffer)buf).getLogOfBitsPerRow());
			}
		} else {
			return 0;
		}
		return -1;
	}
	
	@Override
	@JsonIgnore
	public CompressedList getColumn() {
		return column;
	}
	
}
