package com.compressedlists.impl.buffer.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompressedStringBufferTest {

	@Test
	public void testStringToBytesUTFCustom() {
		String test = "Here is my test";
		byte[] bytes = CompressedStringBuffer.stringToBytesUTFCustom(test);
		String finalString = CompressedStringBuffer.bytesToStringUTFCustom(bytes, 0, bytes.length);
		assertEquals(test, finalString);
		
		finalString = CompressedStringBuffer.bytesToStringUTFCustom(bytes, 10, bytes.length);
		assertEquals("is my test", finalString);
	}
	
	@Test
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
