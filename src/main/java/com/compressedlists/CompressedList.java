package com.compressedlists;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.HdrHistogram.Histogram;

import com.compressedlists.impl.BufferMetadata;
import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.jsoniter.annotation.JsonIgnore;

public interface CompressedList {

	public DataType getDataType();

	public int getSize();
	
	public int getBufferSize(int i);

	void addValue(String str);

	public String getValueDisplay(int i);

	@JsonIgnore
	public int getMaxSize();

	public long getSizeInBytes();
	
	@JsonIgnore
	public Histogram getHistogram();
	
	public boolean hasMaxUniqueValues();
	
	public List<? extends IMemoryBuffer> getBufferList();
	
	@JsonIgnore
	public long getTimeProcessed();
	
	@JsonIgnore
	public long getOriginalSizeInBytes();
	
	public int writeData(RandomAccessFile folder, CompressionType compression, int bufferIndex, BufferMetadata metadata) throws IOException ;
	
	public int readData(RandomAccessFile folder, CompressionType compression, int bufferIndex, int numBytes, int numRecords, BufferMetadata metadata) throws IOException;

	
}