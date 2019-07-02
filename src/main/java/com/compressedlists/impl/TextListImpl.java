package com.compressedlists.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.HdrHistogram.Histogram;

import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.compressedlists.impl.buffer.IStringArrayBuffer;
import com.compressedlists.impl.buffer.MemorySizeInfo;
import com.compressedlists.impl.buffer.text.CompressedStringBuffer;
import com.compressedlists.impl.buffer.text.UncompressedStringBuffer;
import com.compressedlists.TextList;
import com.compressedlists.CompressionType;
import com.compressedlists.DataType;

public class TextListImpl implements TextList {

	Histogram histogram = null; //new Histogram(3600000000000L, 3);
	List<CompressedStringBuffer> buffers;
	private int size = 0;
	private long sizeInBytes = 0;
	private UncompressedStringBuffer lastStringBuffer;
	private UncompressedStringBuffer retrievedStringBuffer;
	private int retrievedBufferNum = -1;
	long totalTimeProcessed = 0l;
	long originalSize = 0l;
	
	public TextListImpl(TextList column) {
		this();
		
		for(int i=0; i<column.getSize(); i++) {
			addValue(column.getValue(i));
		}
		lastStringBuffer = new UncompressedStringBuffer();
		retrievedStringBuffer = new UncompressedStringBuffer();
		if (column.getHistogram() != null) {
			this.histogram = column.getHistogram().copy();
		}
	}
	
	public TextListImpl() {
		buffers = new ArrayList<>();
		lastStringBuffer = new UncompressedStringBuffer();
		retrievedStringBuffer = new UncompressedStringBuffer();
	}
	
	@Override
	public void addValue(String str) {
		long begin = System.nanoTime();
		lastStringBuffer.addValue(str);
		size ++;
//		System.out.println(size);
		if (lastStringBuffer.getSize() == lastStringBuffer.BUFFER_SIZE) {
			// TODO what is this all zeroes doing.  I think this was debugging?
//			boolean allZeroes = true;
//			for (int i=0; i < 10 ; i++) {
//				if (lastStringBuffer.getValue(i) != "0"){
//					allZeroes = false;
//				}
//			}
//			if(allZeroes) {
//				System.out.println("Error Adding " + str + " Found all Zeroes");
//			}
			CompressedStringBuffer buffer = lastStringBuffer.compressToBuffer();
			buffers.add(buffer);
			sizeInBytes += buffer.getSizeInBytes();
			lastStringBuffer = new UncompressedStringBuffer();
		}
		long timeProcessed = System.nanoTime() - begin;
		totalTimeProcessed += timeProcessed;
		if (histogram != null) {
			histogram.recordValue(timeProcessed);
		}
		originalSize+=str.length();
	}
	
	@Override
	public void setValue(int i, String val) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValue(int i) {
		int bufferNum = i >> IStringArrayBuffer.NUM_BITS; // Divide by BUFFER_SIZE
		int index = i & IStringArrayBuffer.BUFFER_SIZE_MODULO_MASK; // Modulo BUFFER_SIZE
		
		if (bufferNum == buffers.size()) {
			// last buffer
			return this.lastStringBuffer.getValue(index);
		} else if (bufferNum == this.retrievedBufferNum) {
//			if (index >= retrievedStringBuffer.getSize()) {
//				System.out.println("Error at index " + index);
//			}
			return this.retrievedStringBuffer.getValue(index);
		} else {
			retrievedBufferNum = bufferNum;
			this.buffers.get(bufferNum).uncompressToBuffer(retrievedStringBuffer);
			return this.retrievedStringBuffer.getValue(index);
		}
		
//		CompressedStringBuffer buffer = buffers.get(bufferNum);
		
//		if (i < buffer.compressedData.length ) {
//			return String.valueOf(buffer.compressedData[i]);
//		} else {
//			return "NAN";
//		}
	}
	
	@Override
	public DataType getDataType() {
		return DataType.TEXT;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public String getValueDisplay(int i) {
		return getValue(i);
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
	public MemorySizeInfo getIndexSizeInBytes() {
		return new MemorySizeInfo();
	}

	@Override
	public long getUniqueValuesSizeInBytes() {
		return 0;
	}

	@Override
	public int getUniqueValuesSize() {
		return -1;
	}

	public Histogram getHistogram() {
		return histogram;
	}

	@Override
	public char[] getUniqueCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getUniqueNGrams(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasMaxUniqueValues() {
		return false;
	}

	@Override
	public long getTimeProcessed() {
		return totalTimeProcessed/1000000;
	}

	@Override
	public long getOriginalSizeInBytes() {
		// TODO Auto-generated method stub
		return originalSize;
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression, int bufferIndex) throws IOException {
		return buffers.get(bufferIndex).writeData(file, compression);
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
