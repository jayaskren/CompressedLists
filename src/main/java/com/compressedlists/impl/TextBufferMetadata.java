package com.compressedlists.impl;

import com.compressedlists.CompressedList;
import com.compressedlists.impl.buffer.text.CompressedStringBuffer;
import com.compressedlists.impl.buffer.text.UncompressedStringBuffer;
import com.jsoniter.annotation.JsonIgnore;

public class TextBufferMetadata extends BufferMetadata {
	@JsonIgnore
	TextListImpl column;
	
	int uncompressedBytes;
	
	public TextBufferMetadata(TextListImpl column, int pos) {
		super(pos);
		this.column = column;
//		((CompressedStringBuffer) column.getBufferList().get(pos)).get;
	}

	@Override
	@JsonIgnore
	public CompressedList getColumn() {
		return column;
	}
}
