package com.compressedlists.impl.buffer.text;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UncompressedStringBufferTest {

	@Test
	public void testAddValue() {
		UncompressedStringBuffer buffer = new UncompressedStringBuffer();
		for (int i=0;i < UncompressedStringBuffer.BUFFER_SIZE ; i++) {
			String val1 = "Test " + i;
			String val2 = "Test Again " + i;
			buffer.addValue(val1);
			assertEquals("Test " + i, val1, buffer.getValue(i));
			
			buffer.setValue(i, val2);
			assertEquals("Test Again " + i, val2, buffer.getValue(i));
			assertEquals(i+1, buffer.getSize());
		}
		
//		// TODO 
//		CompressedStringBuffer compressed = buffer.compressToBuffer();
//		assertTrue("Compressed: " + compressed.getSizeInBytes() +", Uncompressed: " + buffer.getSizeInBytes(), 
//				compressed.getSizeInBytes() < buffer.getSizeInBytes());
//		System.out.println("Compressed: " + compressed.getSizeInBytes() +", Uncompressed: " + buffer.getSizeInBytes());
//		buffer.setValues(new String[0]);
//		assertEquals(0, buffer.getSize());
//		
//		compressed.uncompressToBuffer(buffer);
//		assertEquals(UncompressedStringBuffer.BUFFER_SIZE, buffer.getSize());
//		
//		for(int i=0; i < UncompressedStringBuffer.BUFFER_SIZE; i++) {
//			String val2 = "Test Again " + i;
//			System.out.println(buffer.getValue(i));
//			assertEquals("Failed at i=" + i ,val2, buffer.getValue(i));
//		}
	}

}
