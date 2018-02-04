package com.compressedlists.impl.buffer.integer.unsafe;

import java.lang.reflect.Field;

import com.compressedlists.impl.buffer.IIntMemoryBuffer;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public abstract class AbstractUnsafeBuffer implements IIntMemoryBuffer {
	final int numBytesPerRow;
	
	private static final Unsafe unsafe;
	
	static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	protected final long address;
	protected final byte[] data;
	protected int size;
	
	public AbstractUnsafeBuffer(int numBytesPerRow) {
//		address = unsafe.allocateMemory(BUFFER_SIZE*numBytesPerRow);
		address = unsafe.arrayBaseOffset(byte[].class);
		data = new byte[BUFFER_SIZE*numBytesPerRow];
		this.numBytesPerRow = numBytesPerRow;
	}
	
	@Override
	public void addValue(int value) {
		putValue(unsafe, size++ ,value);
	}
	
	protected abstract void putValue(Unsafe unsafe, int pos, int value);
	protected abstract int getValue(final Unsafe unsafe, int pos);
	
	@Override
	public void setValue(int pos, int value) {
		putValue(unsafe, pos ,value);
	}

	@Override
	public int getValue(int pos) {
		return getValue(unsafe, pos);
		
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSizeInBytes() {
		return size * numBytesPerRow;
	}

	@Override
	public int getWaistedSizeInBytes() {
		return (BUFFER_SIZE - size) * numBytesPerRow;
	}

	public void reset() {
		size = 0;
	}
	
	// TODO  Do I need this?
	public void destroy() {
		unsafe.freeMemory(address);
	}
}
