package com.compressedlists.impl.buffer.integer;

import java.util.Arrays;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;

public class IntegerCompressor {
	
	private static IntegerCompressor instance;
	
	static {
		instance = new IntegerCompressor();
	}
	
	public static IntegerCompressor getInstance() {
		return instance;
	}
	
	protected IntegerCODEC codec;
	protected int[] tempArray;
	
	public IntegerCompressor() {
		codec =  new Composition(new FastPFOR(), new VariableByte());
		tempArray = new int[IIntMemoryBuffer.BUFFER_SIZE *2];
	}
	
	public final int[] compress(int[] inputData) {
		IntWrapper inputOffset = new IntWrapper(0);
        IntWrapper outputOffset = new IntWrapper(0);
		codec.compress(inputData, inputOffset, inputData.length, tempArray, outputOffset);
		return Arrays.copyOf(tempArray, outputOffset.get());
	}
	
	public final void uncompress(int[] compressedData, int[] destArray) {
		IntWrapper inputOffset = new IntWrapper(0);
        IntWrapper outputOffset = new IntWrapper(0);
		codec.uncompress(compressedData, inputOffset, compressedData.length, destArray, outputOffset);
	}
}
