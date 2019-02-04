package com.compressedlists.impl.buffer.integer;

import java.util.Random;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractBufferTest {

	int[] data;
	
	@BeforeEach 
	protected void setUp() throws Exception {
		data = new int[IIntMemoryBuffer.BUFFER_SIZE];
		Random rand = new Random();
		for(int i=0; i< IIntMemoryBuffer.BUFFER_SIZE; i++) {
			data[i] = rand.nextInt(getNumStates());
		}
		
	}
	
	@Test
	public void testAdd() {
		IIntMemoryBuffer buffer = newBuffer();
		for(int i=0; i< 100; i++) {
			buffer.addValue(data[i]);
			assertEquals(data[i], buffer.getValue(i));
		}
		
		assertEquals(100, buffer.getSize());
		assertEquals(getSizeInBytes(), buffer.getSizeInBytes());
//		assertEquals(IBuffer.BUFFER_SIZE -100, buffer.getWaistedSizeInBytes());
		for(int i=100; i< IIntMemoryBuffer.BUFFER_SIZE; i++) {
			buffer.addValue(data[i]);
			assertEquals(data[i], buffer.getValue(i));
		}
		
		assertEquals(IIntMemoryBuffer.BUFFER_SIZE, buffer.getSize());
		assertEquals(getSizeInBytes(), buffer.getSizeInBytes());
		
		// TODO finish this
//		assertEquals(getWaistedSize(), buffer.getWaistedSizeInBytes());
	}
	
	protected abstract IIntMemoryBuffer newBuffer() ;
	
	protected abstract int getNumStates();
	protected abstract int getSizeInBytes();
	protected abstract int getWaistedSize(int size);

}