package com.compressedlists.impl.buffer.text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.buffer.IMemoryBuffer;

public class CompressedStringBuffer implements IMemoryBuffer {

	private int size;
	public byte[] compressedData;
	private int uncompressedByteSize = 0;
	
	public CompressedStringBuffer(int uncompressedByteSize) {
		this.uncompressedByteSize = uncompressedByteSize;
	}
	
	public CompressedStringBuffer(String[] values){
		size = values.length;
		
		byte[] uncompressedData = stringsToBytes(values);
		uncompressedByteSize = uncompressedData.length;
		compressedData = TextCompressor.getInstance().compress(uncompressedData);
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSizeInBytes() {
		return compressedData.length;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return 0;
	}

	public void uncompressToBuffer(UncompressedStringBuffer buffer) {
//		try {
			byte[] bytes = new byte[uncompressedByteSize];
			TextCompressor.getInstance().uncompress(compressedData, bytes);
			String[] vals = bytesToStrings(bytes);
			
			buffer.setValues(vals);
//		} catch (LZFException e) {
//			e.printStackTrace();
//		}
	}
	
	// TODO do more caching instead of allocation
	public static String[] bytesToStrings(byte[] bytes) {
		List<String> returnValues = new ArrayList<>();
		int begin = 0;
		for (int i=0; i<bytes.length ; i+=2) {
			if (bytes[i] == 0x00 && bytes[i+1] == 0x00) {				
				returnValues.add(bytesToStringUTFCustom(bytes, begin, i));
				begin = i+2;
			} 
		}
		String[] returnArray = new String[returnValues.size()];
		return returnValues.toArray(returnArray);
	}
	
	// TODO do more caching instead of allocation
	public static byte[] stringsToBytes(String[] strings) {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			for (int i=0; i<strings.length ; i++) {
				String str = strings[i];
				if (str != null) {
					os.write(stringToBytesUTFCustom(str));
					os.write(0);
					os.write(0);
				}
			}
			return os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}
	
	//http://www.javacodegeeks.com/2010/11/java-best-practices-char-to-byte-and.html
	// another possibility
	//http://psy-lob-saw.blogspot.com/2012/12/encode-utf-8-string-to-bytebuffer-faster.html
	static byte[] stringToBytesUTFCustom(String str) {
		byte[] b = new byte[str.length() << 1];
		for (int i = 0; i < str.length(); i++) {
			char strChar = str.charAt(i);
			int bpos = i << 1;
			b[bpos] = (byte) ((strChar & 0xFF00) >> 8);
			b[bpos + 1] = (byte) (strChar & 0x00FF);
		}
		return b;
	}

	static String bytesToStringUTFCustom(byte[] bytes, int begin, int end) {
		int charBegin = begin >> 1;
		int charEnd = end >> 1;
		int charLength = charEnd - charBegin;
		char[] buffer = new char[charLength];
		for (int i = charBegin; i < charEnd; i++) {
			int bpos = i << 1;
			char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));
			buffer[i - charBegin] = c;
		}
		return new String(buffer);
	}

	@Override
	public void reset() {
		size = 0;
	}

	@Override
	public int writeData(RandomAccessFile file, CompressionType compression) throws IOException {
		switch (compression) {
		case GZIP:
			
			break;
		case LZ4:
			
			break;
		case SNAPPY:
			
			break;
		case ZSTD:
			
			break;
		default:
			file.write(compressedData, 0, compressedData.length);
			return compressedData.length;
		}
		return 0;
	}

	@Override
	public int readFromFile(RandomAccessFile file, CompressionType compression, int numBytes, int numRecords)
			throws IOException {
		compressedData = new byte[numBytes];
		
		int numBytesRead = file.read(compressedData, 0, numBytes);
		size = numRecords;
		return numBytesRead; 
	}

	public int getUncompressedByteSize() {
		return uncompressedByteSize;
	}
	
	public int getCompressedByteSize() {
		return compressedData.length;
	}
}
