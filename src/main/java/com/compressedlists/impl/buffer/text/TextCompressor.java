package com.compressedlists.impl.buffer.text;

import java.util.Arrays;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;


public class TextCompressor {

	static TextCompressor compressor;
	static LZ4Compressor lz4Compressor; 
	static LZ4FastDecompressor lz4Decompressor;

	
	static {
		compressor = new TextCompressor();
	}

	private static byte[] tempArray;
	
	private TextCompressor(){
		lz4Decompressor = LZ4Factory.fastestInstance().fastDecompressor();
		lz4Compressor = LZ4Factory.fastestInstance().fastCompressor();
		tempArray = new byte[IIntMemoryBuffer.BUFFER_SIZE *2];
	}
	
	public static TextCompressor getInstance() {
		return compressor;
	}
	
	public final void uncompress(byte[] compressedData, byte[] destArray) {
//		try {
//			LZFDecoder.decode(compressedData, destArray);
//		} catch (LZFException e) {
//			e.printStackTrace();
//		}
		lz4Decompressor.decompress(compressedData, destArray);
	}
	
	public final byte[] compress(byte[] uncompressedData) {
//		return LZFEncoder.encode(uncompressedData);
		
//		int length = lz4Compressor.compress(uncompressedData, tempArray);
//		return Arrays.copyOf(tempArray, length);
		return lz4Compressor.compress(uncompressedData);
	}
}
