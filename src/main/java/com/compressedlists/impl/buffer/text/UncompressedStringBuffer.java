package com.compressedlists.impl.buffer.text;

import com.compressedlists.impl.buffer.IStringArrayBuffer;

public class UncompressedStringBuffer implements IStringArrayBuffer {

	private String[] values;
	private int size = 0;
	private int sizeInBytes = 0;
	
	public UncompressedStringBuffer(String[] values) {
		setValues(values);
	}
	
	public UncompressedStringBuffer() {
		values = new String[UncompressedStringBuffer.BUFFER_SIZE];
	}
	
	@Override
	public void addValue(String value) {
		values[size] = value;
		sizeInBytes += (2*value.length());
		size ++;
	}
	
	void setValues(String[] values) {
		this.values = values;
		this.size = values.length;
		int sizeInBytes = 0;
		for (String val: values) {
			sizeInBytes += (2*val.length());
		}
		this.sizeInBytes = sizeInBytes;
	}
	
	@Override
	public void setValue(int pos, String value) {
		values[pos] = value;
	}

	@Override
	public String getValue(int pos) {
		return values[pos];
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSizeInBytes() {
		return sizeInBytes;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return 0;
	}
	
	public CompressedStringBuffer compressToBuffer( ) {
		return new CompressedStringBuffer(values);
	}

	@Override
	public void reset() {
		size = 0;
	}
}
