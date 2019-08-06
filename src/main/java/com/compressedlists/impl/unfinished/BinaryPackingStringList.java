package com.compressedlists.impl.unfinished;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.compressedlists.CompressionType;
import com.compressedlists.impl.AbstractDictionaryStringList;
import com.compressedlists.impl.BufferMetadata;
import com.compressedlists.impl.buffer.IMemoryBuffer;
import com.compressedlists.impl.buffer.MemorySizeInfo;

import me.lemire.integercompression.BinaryPacking;
import me.lemire.integercompression.IntWrapper;

//TODO Finish this

public class BinaryPackingStringList extends AbstractDictionaryStringList {

	public BinaryPackingStringList() {
		super();
		// TODO Auto-generated constructor stub
	}

	BinaryPacking bp = new BinaryPacking();
	final int BLOCK_SIZE = 8192*4;
	int lastBlockIndex = 0;
	int lastSubBlockIndex = 0;
	int lastSubBlockCompressedIndex = 0;
	List<int[]> blocks;
	
	int[] lastBlock = new int[BLOCK_SIZE];
	int[] lastBlockCompressed = new int[BLOCK_SIZE];
	int[] tempArray = new int[BLOCK_SIZE];
	IntList indexList = new IntArrayList() ;
	
	
	@Override
	public void setValue(int i, String val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addIndex(int index) {
		lastBlock[lastSubBlockIndex] = index;
		
		
		if(lastSubBlockIndex==BLOCK_SIZE) {
			//Encode and reset block indexes
			lastSubBlockIndex = 0;
			int[] newBlock = new int[BLOCK_SIZE];
			IntWrapper inWrapper = new IntWrapper();
			IntWrapper outWrapper = new IntWrapper();
			bp.compress(lastBlock, inWrapper, BLOCK_SIZE, newBlock, outWrapper);
			if (inWrapper.intValue() < BLOCK_SIZE) {
				// we reached the end of lastBlockCompressed  and could not write all of lastBlock
				blocks.add(lastBlockCompressed);
				lastSubBlockCompressedIndex = 0;
				lastSubBlockIndex++;
				
				// Compress the rest of the data
				inWrapper.set(0);
				outWrapper.set(0);
				bp.compress(lastBlock, inWrapper, BLOCK_SIZE, newBlock, outWrapper);
				lastSubBlockCompressedIndex +=  outWrapper.intValue();
			} else {
				lastSubBlockCompressedIndex +=  outWrapper.intValue();
			}
			
		} else {
			lastSubBlockIndex++;
		}
		
	}

	@Override
	public int getIndexValue(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemorySizeInfo getIndexSizeInBytes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getSizeInBytes() {
		// TODO Auto-generated method stub
		return 0;
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
	public int writeData(RandomAccessFile folder, CompressionType compression, int bufferIndex, BufferMetadata metadata) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readData(RandomAccessFile folder, CompressionType compression, int bufferIndex, int numBytes, int numRecords, BufferMetadata metadata) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<? extends IMemoryBuffer> getBufferList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBufferSize(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

}
