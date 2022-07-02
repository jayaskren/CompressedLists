package com.compressedlists.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.compressedlists.impl.buffer.BitUtil;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.compressedlists.impl.buffer.IStringArrayBuffer;

public class BitUtilTest {

//	public void testClearBits() {
//		int begin = 0b11111111;
//		byte answer = BitUtil.clearBits((byte)begin, 0, 1);
//		assertEquals(0b11111110, answer);
//	}
	
//	public void testPackUnpack1Bit() {
//		int numBits = 1;
////		System.out.println(BitSet.valueOf(new byte[] { BitUtil.packUnsigned(7, (byte)0, 0, 1)}));
//		
//		byte packedByte = (byte) 0;
//		for (int i=0; i<8 ; i++) {
//			for(int j=0; j < 2; j++) {
//				packedByte = BitUtil.packUnsigned(j, packedByte, i, numBits);
//				System.out.println(BitSet.valueOf(new byte[] { packedByte}));
//				assertEquals("Error when:i=" + i + ", j=" + j, 
//						j, BitUtil.unpackUnsigned(packedByte, i, numBits));
//			}
//		}
//		
//		for (int i=7; i>=0 ; i--) {
//			for(int j=0; j < 2; j++) {
//				packedByte = BitUtil.packUnsigned(j, packedByte, i, numBits);
//				assertEquals("Error when:i=" + i + ", j=" + j, 
//						j, BitUtil.unpackUnsigned(packedByte, i, numBits));
//			}
//		}
//		
//		packedByte = BitUtil.packUnsigned(1, packedByte, 0, numBits);
//		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 0, numBits));
//		assertEquals(1, (int)packedByte);
//		packedByte = BitUtil.packUnsigned(1, packedByte, 2, numBits);
//		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 2, numBits));
//		assertEquals(5, (int)packedByte);
//		packedByte = BitUtil.packUnsigned(1, packedByte, 6, numBits);
//		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 6, numBits));
//		assertEquals(69, (int)packedByte);
//		packedByte = BitUtil.packUnsigned(1, packedByte, 4, numBits);
//		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 4, numBits));
//		assertEquals(85, (int)packedByte);
//		
//		packedByte = (byte) 0;//Is this cheating?
//		
//		packedByte = BitUtil.packUnsigned(1, packedByte, 5, numBits);
//		assertEquals(32, (int)packedByte);
//		packedByte = BitUtil.packUnsigned(1, packedByte, 3, numBits);
//		assertEquals(40, (int)packedByte);
//		packedByte = BitUtil.packUnsigned(1, packedByte, 1, numBits);
//		assertEquals(42, (int)packedByte);
//		packedByte = BitUtil.packUnsigned(1, packedByte, 7, numBits);
//		assertEquals(-86, (int)packedByte);
//	}
	
//	public void testPackUnpack2Bits() {
//		int numBits = 2;
//		byte packedByte = (byte) 0;
//		for (int i=0; i<4 ; i++) {
//			for(int j=0; j < 4; j++) {
//				packedByte = BitUtil.packUnsigned(j, packedByte, i, numBits);
//				assertEquals("Error when:i=" + i + ", j=" + j, 
//						j, BitUtil.unpackUnsigned(packedByte, i, numBits));
//			}
//		}
//		
//		for (int i=3; i>=0 ; i--) {
//			for(int j=0; j < 4; j++) {
//				packedByte = BitUtil.packUnsigned(j, packedByte, i, numBits);
//				assertEquals("Error when:i=" + i + ", j=" + j, 
//						j, BitUtil.unpackUnsigned(packedByte, i, numBits));
//			}
//		}
//		
////		packedByte = BitUtil.packUnsigned(1, packedByte, 0, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 0, numBits));
////		assertEquals(1, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 2, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 2, numBits));
////		assertEquals(5, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 6, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 6, numBits));
////		assertEquals(69, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 4, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 4, numBits));
////		assertEquals(85, (int)packedByte);
////		
////		packedByte = (byte) 0;
////		
////		packedByte = BitUtil.packUnsigned(1, packedByte, 5, numBits);
////		assertEquals(32, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 3, numBits);
////		assertEquals(40, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 1, numBits);
////		assertEquals(42, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 7, numBits);
////		assertEquals(-86, (int)packedByte);
//	}
	
//	public void testPackUnpack4Bit() {
//		int numBits = 4;
//		byte packedByte = (byte) 0;
//		for (int i=0; i<2 ; i++) {
//			for(int j=0; j < 16; j++) {
//				packedByte = BitUtil.packUnsigned(j, packedByte, i, numBits);
//				assertEquals("Error when:i=" + i + ", j=" + j, 
//						j, BitUtil.unpackUnsigned(packedByte, i, numBits));
//			}
//		}
//		
//		for (int i=1; i>=0 ; i--) {
//			for(int j=0; j < 16; j++) {
//				packedByte = BitUtil.packUnsigned(j, packedByte, i, numBits);
//				assertEquals("Error when:i=" + i + ", j=" + j, 
//						j, BitUtil.unpackUnsigned(packedByte, i, numBits));
//			}
//		}
//		
////		packedByte = BitUtil.packUnsigned(1, packedByte, 0, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 0, numBits));
////		assertEquals(1, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 2, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 2, numBits));
////		assertEquals(5, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 6, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 6, numBits));
////		assertEquals(69, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 4, numBits);
////		assertEquals(1, BitUtil.unpackUnsigned(packedByte, 4, numBits));
////		assertEquals(85, (int)packedByte);
////		
////		packedByte = (byte) 0;
////		
////		packedByte = BitUtil.packUnsigned(1, packedByte, 5, numBits);
////		assertEquals(32, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 3, numBits);
////		assertEquals(40, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 1, numBits);
////		assertEquals(42, (int)packedByte);
////		packedByte = BitUtil.packUnsigned(1, packedByte, 7, numBits);
////		assertEquals(-86, (int)packedByte);
//	}
	
	@Test
	public void testPackUnpackByte() {
		byte packedByte = (byte) 0;
//		assertEquals(128, BitUtil.packByteUnsigned(128));
		for (int i=0; i<256 ; i++) {
			packedByte = BitUtil.packByteUnsigned(i);
			assertEquals("Error when: i=" + i, i, BitUtil.unpackByteUnsigned(packedByte));
		}
	}
	
//	public void testNumBits() {
//		assertEquals(0, BitUtil.numBits(0));
//		assertEquals(1, BitUtil.numBits(1));
//		assertEquals(2, BitUtil.numBits(2));
//		assertEquals(2, BitUtil.numBits(3));
//		assertEquals(3, BitUtil.numBits(4));
//		assertEquals(3, BitUtil.numBits(5));
//		assertEquals(4, BitUtil.numBits(8));
//		assertEquals(4, BitUtil.numBits(9));
//		assertEquals(5, BitUtil.numBits(16));
//		assertEquals(5, BitUtil.numBits(17));
//		assertEquals(5, BitUtil.numBits(18));
//		assertEquals(5, BitUtil.numBits(31));
//		assertEquals(6, BitUtil.numBits(32));
//		assertEquals(7, BitUtil.numBits(64));
//		assertEquals(8, BitUtil.numBits(128));
//	}
	
	@Test
	public void testDivide() {
		for (int i=0; i <256 ;i++){
			int val = i * IIntMemoryBuffer.BUFFER_SIZE +i;
			assertEquals(val / IStringArrayBuffer.BUFFER_SIZE, val >> IStringArrayBuffer.NUM_BITS);
		}
	}
	
	@Test
	public void testModulo() {
		for (int i=0; i <256 ;i++){
			int val = i* IIntMemoryBuffer.BUFFER_SIZE +i;
			assertEquals(val % IStringArrayBuffer.BUFFER_SIZE, val & (IStringArrayBuffer.BUFFER_SIZE_MODULO_MASK));
		}
	}
	
	@Test
	public void testNumBitsBinLog() {
		for (int i=2; i<100000; i++) {
//			System.out.println(i + ": " +(BitUtil.binlog(BitUtil.binlog(i)) + 1) + " =? "  + BitUtil.numBits(i));
			assertEquals("Not Equal for i = "+i, BitUtil.binlog(BitUtil.binlog(i)) + 1, BitUtil.numBits(i));
		}
	}
	
	public static void main(String[] args) {
		int i = 129;
		int j = 130;
		char val = (char) ((i & 0xff) | ((j & 0xff) << 8));
		System.out.println((int)val);
		
		System.out.println(val & 0xff);
		System.out.println((val>> 8) & 0xff);
	}
}
