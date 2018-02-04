package com.compressedlists.impl.buffer.text;

import junit.framework.TestCase;

public class CompressedStringBufferTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStringToBytesUTFCustom() {
		String test = "Here is my test";
		byte[] bytes = CompressedStringBuffer.stringToBytesUTFCustom(test);
		String finalString = CompressedStringBuffer.bytesToStringUTFCustom(bytes, 0, bytes.length);
		assertEquals(test, finalString);
		
		finalString = CompressedStringBuffer.bytesToStringUTFCustom(bytes, 10, bytes.length);
		assertEquals("is my test", finalString);
	}
	
	public void testStringsToBytes() {
		String[] text = new String[2];
		for(int i=0; i < text.length; i++) {
			text[i] = "Test " + i;
		}
		byte[] bytes = CompressedStringBuffer.stringsToBytes(text);
		assertEquals(28, bytes.length);
		String[] newStrings= CompressedStringBuffer.bytesToStrings(bytes);
		assertEquals(text.length, newStrings.length);
		
		text = new String[100000];
		
		for(int i=0; i <text.length ; i++) {
			text[i] = "Test " + i;
		}
		
		bytes = CompressedStringBuffer.stringsToBytes(text);
		newStrings= CompressedStringBuffer.bytesToStrings(bytes);
		assertEquals(text.length, newStrings.length);
	}
}
