package com.compressedlists.impl.buffer;

import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer0;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer1;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer16;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer2;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer32;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer4;
import com.compressedlists.impl.buffer.integer.array.UnsignedIntArrayBuffer8;

public class ArrayBufferFactory {
	// TODO Make a Cache of Buffers.  When we are done, put one back
	
	public ArrayBufferFactory() {
		
	}
	
	public IIntMemoryBuffer createBuffer(int numBits) {
		switch (numBits) {
		case -1:
//			return new UnsignedIntArrayBuffer0();
		case 0: // 0-1
//			return new BitSetMemoryBuffer1();
				return new UnsignedIntArrayBuffer1();
		case 1: // 0-3
//			return new BitSetMemoryBuffer2();
				return new UnsignedIntArrayBuffer2();
		case 2: // 0-15
//			return new BitSetMemoryBuffer4();
				return new UnsignedIntArrayBuffer4();
		case 3: // 0-255
			return new UnsignedIntArrayBuffer8();
		case 4: // 0-65,535
			return new UnsignedIntArrayBuffer16();
		case 5: // 0-2,147,483,647  (Max Int)
			return new UnsignedIntArrayBuffer32();
		default:
			break;
		}
		
		return null;
		
	}
	
	
	
}
