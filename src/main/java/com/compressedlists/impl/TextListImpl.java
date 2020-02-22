package com.compressedlists.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.HdrHistogram.Histogram;

import com.compressedlists.impl.buffer.BitUtil;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
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
	private long compressedSizeInBytes = 0;
	UncompressedStringBuffer lastStringBuffer;
	private UncompressedStringBuffer retrievedStringBuffer;
	private int retrievedBufferNum = -1;
	long totalTimeProcessed = 0l;
	long originalSize = 0l;
	
	public TextListImpl(List<TextBufferMetadata> mdList, long originalSizeInBytes) {
		this.buffers = new ArrayList<>();
		for (int i=0; i < mdList.size() - 1; i++) {
			buffers.add(new CompressedStringBuffer(mdList.get(i).getUncompressedByteSize()));
		}
		lastStringBuffer=new UncompressedStringBuffer();
		retrievedStringBuffer = new UncompressedStringBuffer();
		this.originalSize = originalSizeInBytes;
	}
	
	public TextListImpl(TextList column) {
		this();
		lastStringBuffer = new UncompressedStringBuffer();
		retrievedStringBuffer = new UncompressedStringBuffer();
		
		for(int i=0; i<column.getSize(); i++) {
			addValue(column.getValue(i));
		}
		
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
//		System.out.println(size);
		
		if (lastStringBuffer.getSize() == lastStringBuffer.BUFFER_SIZE) {
			CompressedStringBuffer buffer = lastStringBuffer.compressToBuffer();
			buffers.add(buffer);
			compressedSizeInBytes += buffer.getSizeInBytes();
			lastStringBuffer = new UncompressedStringBuffer();
		}
		long timeProcessed = System.nanoTime() - begin;
		totalTimeProcessed += timeProcessed;
		if (histogram != null) {
			histogram.recordValue(timeProcessed);
		}
		size ++;
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
			try {
				return this.lastStringBuffer.getValue(index);
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				throw e;
			}
		} else if (bufferNum == this.retrievedBufferNum) {
//			if (index >= retrievedStringBuffer.getSize()) {
//				System.out.println("Error at index " + index);
//			}
			return this.retrievedStringBuffer.getValue(index);
		} else {
			retrievedBufferNum = bufferNum;
			try {
				this.buffers.get(bufferNum).uncompressToBuffer(retrievedStringBuffer);
				return this.retrievedStringBuffer.getValue(index);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				throw e;
			}
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
		return compressedSizeInBytes + lastStringBuffer.getSizeInBytes() + retrievedStringBuffer.getSizeInBytes();
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
		return 0;
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
	public int writeData(RandomAccessFile file, CompressionType compression, int bufferIndex, BufferMetadata metadata) throws IOException {
		if (buffers.size() == bufferIndex ) {
			CompressedStringBuffer buffer = lastStringBuffer.compressToBuffer();
			TextBufferMetadata md = (TextBufferMetadata) metadata;
			md.setUncompressedByteSize(buffer.getUncompressedByteSize());
			md.setCompressedByteSize(buffer.getCompressedByteSize());
			return buffer.writeData(file, compression);
		}
		return buffers.get(bufferIndex).writeData(file, compression);
	}

	@Override
	public int readData(RandomAccessFile file, CompressionType compression, int bufferIndex, int numBytes, int numRecords, BufferMetadata metadata) throws IOException {
		size += numRecords;
		if (bufferIndex == buffers.size()) {
			CompressedStringBuffer tempBuffer = new CompressedStringBuffer(((TextBufferMetadata) metadata).uncompressedByteSize);
			int numBytesRead = tempBuffer.readFromFile(file, compression, numBytes, numRecords);
			tempBuffer.uncompressToBuffer(this.lastStringBuffer);
			return numBytesRead;
		} 
		int bytesRead = buffers.get(bufferIndex).readFromFile(file, compression, numBytes, numRecords);
		this.compressedSizeInBytes += bytesRead;
		return bytesRead;
	}
	
	@Override
	public List<? extends IMemoryBuffer> getBufferList() {
		return buffers;
	}

	@Override
	public int getBufferSize(int i) {
		if (i == buffers.size()) {
			return lastStringBuffer.getSize();
		} else {
			return buffers.get(i).getSize();
		}
		
	}
}
