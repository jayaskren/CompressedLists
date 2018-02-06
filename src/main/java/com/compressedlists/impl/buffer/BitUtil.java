package com.compressedlists.impl.buffer;


public class BitUtil {

	private static int[] mask = new int[]{1, 7, 15};
	private static int[] clearMask = new int[]{};
	
//	public int extractValue(byte data, int start, int end) {
//		
//	}
	
//	public byte setValue(byte data, int value, int pos, int length) {
//		// http://stackoverflow.com/questions/4550891/packing-values-into-a-single-int
//		// https://github.com/real-logic/simple-binary-encoding/blob/master/main/java/uk/co/real_logic/sbe/codec/java/CodecUtil.java
//		// http://mechanical-sympathy.blogspot.com/2012/10/compact-off-heap-structurestuples-in.html
//		
//		// The following looks promising
//		// http://www.herongyang.com/Java/Bit-String-Stored-in-Byte-Array.html
//		return (byte)((0xFF & value) << pos*length);
//	}
//	
//	private static int getBit(byte[] data, int pos) {
//	      int posByte = pos/8; 
//	      int posBit = pos%8;
//	      byte valByte = data[posByte];
//	      int valInt = valByte>>(8-(posBit+1)) & 0x0001;
//	      return valInt;
//	}
//	 
//	private static void setBit(byte[] data, int pos, int val) {
//	      int posByte = pos/8; 
//	      int posBit = pos%8;
//	      byte oldByte = data[posByte];
//	      oldByte = (byte) (((0xFF7F>>posBit) & oldByte) & 0x00FF);
//	      byte newByte = (byte) ((val<<(8-(posBit+1))) | oldByte);
//	      data[posByte] = newByte;
//	   }
	
//	public static long pack(int c1, int c2, int c3, int c4)
//	{
//		
//	    return ((0xFFL & c1) << 24) | ((0xFFL & c2) << 16) | ((0xFFL & c3) << 8) | (0xFFL & c4);
//	}
	
	public static byte packUnsigned(int val, byte inputByte, int pos, int numBits) {
		System.out.println("i=" + pos + ", j=" + val);
		byte clearedByte = clearBits(inputByte, pos, numBits);
		return (byte) (clearedByte | ((0xff & val) << (pos*numBits)));
	}
	
	public static byte clearBits(byte inputByte, int pos, int numBits){
		int mask = (1 << (pos));
//		mask = mask >> (8-pos);
		byte dest = (byte)(inputByte & ~(1 << pos)); // first zero out the position
		
		return dest;
	}
	
	public static int unpackUnsigned(byte val, int pos, int numBits) {
		return (int)((val >> pos) & 0b11111111) & mask[numBits-1];
	}
	
	public static byte packByteUnsigned(int val) {
		return (byte) (0b11111111 & val);
	}
	
	public static int unpackByteUnsigned(byte val) {
		return (int)((val)  & 0b11111111);
	}
	
	// The following are different ways to calculate logarithm base 2
	@Deprecated
	public static int logSlow(int x) {
	    return (int) (Math.log(x) / Math.log(2));
	}
	
	public static int binlog( int bits ) {// returns 0 for bits=0 
	    int log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}
	
	public static int numBits(int i) {
		if (i < 2) {
			return 0;
		}
		return 31 - Integer.numberOfLeadingZeros((31 - Integer.numberOfLeadingZeros(i))<<1);
	}
	
	public static int log2nlz( int bits ) {
	    if( bits == 0 )
	        return 0; // or throw exception
	    return 31 - Integer.numberOfLeadingZeros( bits );
	}
	
//	public static byte pack1(int val, byte inputByte, int pos) {
//		return (byte) (inputByte | (0xFF & val) << pos);
//	}
//	
//	public static byte pack2(int val, byte inputByte, int pos) {
//		
//	}
//	
//	public static byte pack4(int val, byte inputByte, int pos) {
//		return (byte) (inputByte | (0xFF & val) << (pos*4));
//	}
//
//	public static int unpack1(byte val, int pos) {
//		return (val >> pos) & (byte)1;
//	}
//	
//	public static int unpack2(byte val, int pos) {
//		return (val >> pos) & (byte)7;
//	}
//
//	public static int unpack4(byte val, int pos) {
//		return (val >> pos) & (byte)15;
//	}
//
//	

}
