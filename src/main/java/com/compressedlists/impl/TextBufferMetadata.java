package com.compressedlists.impl;

import com.compressedlists.CompressedList;
import com.compressedlists.impl.buffer.text.CompressedStringBuffer;
import com.compressedlists.impl.buffer.text.UncompressedStringBuffer;
import com.jsoniter.annotation.JsonIgnore;

public class TextBufferMetadata extends BufferMetadata {
	@JsonIgnore
	TextListImpl column;
	
	int uncompressedByteSize;
	int compressedByteSize;
	
	public TextBufferMetadata(int pos, int start, int numRows, 
			int uncompressedSize, int compressedByteSize) {
		super(pos, start, numRows);
		this.uncompressedByteSize = uncompressedSize;
		this.compressedByteSize = compressedByteSize;
	}
	
	public TextBufferMetadata(TextListImpl column, int pos) {
		super(pos);
		this.column = column;
		if (column.buffers.size() == pos) {
			uncompressedByteSize = column.lastStringBuffer.getSizeInBytes();
			compressedByteSize = 0;
		} else {
			try {
				uncompressedByteSize = column.buffers.get(pos).getUncompressedByteSize();
				compressedByteSize = column.buffers.get(pos).getCompressedByteSize();
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Error with column containing " + 
						column.getValue(0) + ", " + column.getValue(1) + 
						", " + column.getValue(2) + ", " + column.getValue(3));
				e.printStackTrace();
				throw e;
			}
		}
//		((CompressedStringBuffer) column.getBufferList().get(pos)).get;
	}

	public int getUncompressedByteSize() {
		return uncompressedByteSize;
	}

	public void setUncompressedByteSize(int uncompressedByteSize) {
		this.uncompressedByteSize = uncompressedByteSize;
	}

	public int getCompressedByteSize() {
		return compressedByteSize;
	}

	public void setCompressedByteSize(int compressedByteSize) {
		this.compressedByteSize = compressedByteSize;
	}

	
}
