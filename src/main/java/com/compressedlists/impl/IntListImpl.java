package com.compressedlists.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.HdrHistogram.Histogram;

import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.compressedlists.impl.buffer.IStringArrayBuffer;
import com.compressedlists.impl.buffer.integer.CompressedIntegerBuffer;
import com.compressedlists.impl.buffer.integer.IntegerParser;
import com.compressedlists.impl.buffer.integer.UncompressedIntegerBuffer;
import com.compressedlists.IntList;
import com.compressedlists.CompressionType;
import com.compressedlists.DataType;

public class IntListImpl implements IntList {

	List<CompressedIntegerBuffer> buffers;
	private int size = 0;
	private long sizeInBytes = 0;
	private int retrievedBufferNum = -1;
	private UncompressedIntegerBuffer lastBuffer;
	private UncompressedIntegerBuffer retrievedBuffer;
	Histogram histogram = null; //new Histogram(3600000000000L, 3);
	long totalTimeProcessed = 0l;
	
	public IntListImpl(IntList column) {
		this();
		
		for(int i=0; i<column.getSize(); i++) {
			addValue(column.getValue(i));
		}
		lastBuffer = new UncompressedIntegerBuffer();
		retrievedBuffer = new UncompressedIntegerBuffer();
		if (column.getHistogram() != null) {
			this.histogram = column.getHistogram().copy();
		}
	}
	
	public IntListImpl() {
		buffers = new ArrayList<>();
		lastBuffer = new UncompressedIntegerBuffer();
		retrievedBuffer = new UncompressedIntegerBuffer();
	}

	@Override
	public boolean addValue(int value) {
		lastBuffer.addValue(value);
		size ++;
		
		if (lastBuffer.getSize() == lastBuffer.BUFFER_SIZE) {
			
			CompressedIntegerBuffer buffer = lastBuffer.compressToBuffer();
			buffers.add(buffer);
			sizeInBytes += buffer.getSizeInBytes();
			lastBuffer = new UncompressedIntegerBuffer();
		}
		return true;
	}

	@Override
	public int getValue(int i) {
		int bufferNum = i >> IStringArrayBuffer.NUM_BITS; // Divide by BUFFER_SIZE
		int index = i & IStringArrayBuffer.BUFFER_SIZE_MODULO_MASK; // Modulo BUFFER_SIZE
		
		if (bufferNum == buffers.size()) {
			// last buffer
			return this.lastBuffer.getValue(index);
		} else if (bufferNum == this.retrievedBufferNum) {
			return this.retrievedBuffer.getValue(index);
		} else {
			retrievedBufferNum = bufferNum;
			retrievedBuffer = this.buffers.get(bufferNum).uncompress();
			return this.retrievedBuffer.getValue(index);
		}
	}
	
	@Override
	public DataType getDataType() {
		return DataType.INT;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public String getValueDisplay(int i) {
		return Integer.toString(getValue(i));
	}

	@Override
	public int getMaxSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	public long getSizeInBytes() {
		return sizeInBytes;
	}

	@Override
	public Histogram getHistogram() {
		return histogram;
	}

	@Override
	public void addValue(String str) {
		long time = System.nanoTime();
		int val = IntegerParser.parseInt(str);
		
		addValue(val);
		long timeProcessed = System.nanoTime() - time;
		totalTimeProcessed += timeProcessed;
		if (histogram != null) {
			histogram.recordValue(timeProcessed);
		}
	}

	@Override
	public int calculateAverage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IntList add(int val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntList add(IntList lst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasMaxUniqueValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getTimeProcessed() {
		return totalTimeProcessed/1000000;
	}

	@Override
	public long getOriginalSizeInBytes() {
		return getSize()*4;
	}

	@Override
	public int getMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int writeData(RandomAccessFile folder, CompressionType compression, int bufferIndex) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readData(File folder, CompressionType compression, int bufferIndex, int numBytes, int numRecords) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<? extends IMemoryBuffer> getBufferList() {
		return buffers;
	}
}
