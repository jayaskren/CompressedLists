package com.compressedlists.impl.unique;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * 
 * Array/List/Buffer that holds
	Pointer to string in buffer (0-64,536) (2 bytes)
		Sign can mean the buffer we are pointing to
			Byte buffer (ascii)
			Char buffer (utf-8) 
		Int
	Length of buffer (2 bytes)
		Char
	Hash Code (4 bytes) (Test with and without)
		Int
*/


public class UniqueStringList {
	List<ByteBuffer> pointers;
	List<ByteBuffer> characters;
	int size = 0;
	
	public void add(String val) {
		
		size++;
	}
	
	public String get(int pos) {
		return "";
	}
	
	public void set(int pos, String val) {
		
	}
}
