package com.compressedlists.impl.buffer;

import com.compressedlists.impl.buffer.integer.bitset.BitSetMemoryBuffer1;
import com.compressedlists.impl.buffer.integer.bitset.BitSetMemoryBuffer2;
import com.compressedlists.impl.buffer.integer.bitset.BitSetMemoryBuffer4;
import com.compressedlists.impl.buffer.integer.unsafe.SignedUnsafeBuffer32;
import com.compressedlists.impl.buffer.integer.unsafe.UnsignedUnsafeBuffer16;
import com.compressedlists.impl.buffer.integer.unsafe.UnsignedUnsafeBuffer8;

public class UnsafeBufferFactory {
	// TODO Make a Cache of Buffers.  When we are done, put one back
	
	public UnsafeBufferFactory() {
		
	}
	
	public IIntMemoryBuffer createBuffer(int numBits) {
					
		switch (numBits) {
		case 0:
			return new BitSetMemoryBuffer1();
		case 1:
			return new BitSetMemoryBuffer2();
		case 2:
			return new BitSetMemoryBuffer4();
		case 3:
//			return new UnsignedIntArrayBuffer8();
			return new UnsignedUnsafeBuffer8();
		case 4:
//			return new UnsignedIntArrayBuffer16();
			return new UnsignedUnsafeBuffer16();
		case 5:
//			return new UnsignedIntArrayBuffer32();
			return new SignedUnsafeBuffer32();
		default:
			break;
		}
		
		return null;
		
	}
	
}
