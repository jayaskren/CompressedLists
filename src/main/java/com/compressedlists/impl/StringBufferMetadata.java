package com.compressedlists.impl;

import com.compressedlists.CompressedList;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.jsoniter.annotation.JsonIgnore;

public class StringBufferMetadata extends BufferMetadata {
	
	int numBitsPerRow;
	
	public StringBufferMetadata(int pos, int start, int numRows, int numBitsPerRow) {
		super(pos, start, numRows);
		this.numBitsPerRow = numBitsPerRow;
	}
	
	
	public StringBufferMetadata(StringListImpl column, int pos) {
		super(pos);
		IMemoryBuffer buf = column.getBufferList().get(pos);
		this.numRows = buf.getSize();
//		this.start = 
		if (column.getUniqueValuesSize() >1) {
			if (buf instanceof IIntMemoryBuffer) {
				numBitsPerRow = Math.max(8, (int)Math.pow(2, ((IIntMemoryBuffer)buf).getLogOfBitsPerRow()));
			} else {
				numBitsPerRow = -1;
			}
			
		} else {
			numBitsPerRow = 0;
		}
	}
	
	public int getNumBitsPerRow() {
		return numBitsPerRow;
	}
	
//	@Override
//	@JsonIgnore
//	public CompressedList getColumn() {
//		return column;
//	}
	
}
