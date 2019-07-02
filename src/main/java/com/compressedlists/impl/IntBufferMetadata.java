package com.compressedlists.impl;

import com.compressedlists.CompressedList;
import com.jsoniter.annotation.JsonIgnore;

public class IntBufferMetadata extends BufferMetadata {
	@JsonIgnore
	IntListImpl column;
	
	public IntBufferMetadata(IntListImpl column, int pos) {
		super(pos);
		this.column = column;
	}

	@Override
	@JsonIgnore
	public CompressedList getColumn() {
		return column;
	}

}
