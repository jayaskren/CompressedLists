package com.compressedlists.impl;

import java.util.ArrayList;
import java.util.List;

import com.compressedlists.CompressedList;
import com.compressedlists.DataType;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonIgnore;
import com.jsoniter.annotation.JsonProperty;

public class ColumnMetadata {
	private String name;
	@JsonIgnore
	private CompressedList column;
	
	private List<String> uniqueValues;
	private List<BufferMetadata> bufferMetadata;
	private long originalSizeInBytes;
	
	@JsonCreator
	public ColumnMetadata( @JsonProperty("uniqueValues") List<String> uniqueValues, 
			 @JsonProperty("bufferMetadata") List<BufferMetadata> bufferMetadata) {
		this.uniqueValues = uniqueValues;
		this.bufferMetadata = bufferMetadata;
	}
	
	public ColumnMetadata(String name, CompressedList column) {
		this.column = column;
		this.name = name;
		this.originalSizeInBytes = column.getOriginalSizeInBytes();
		if (column instanceof StringListImpl) {
			StringListImpl col = (StringListImpl) column;
			uniqueValues = col.getUniqueValues();
			if (uniqueValues.size() > 1) {
				System.out.println("Test " + name + ": " + uniqueValues.size());
			}
			bufferMetadata = new ArrayList<>(col.bufferList.size());
			for (int i=0; i <col.bufferList.size() ; i++) {
				bufferMetadata.add(new StringBufferMetadata(col, i));
			}
		} else if (column instanceof TextListImpl) {
			TextListImpl col = (TextListImpl) column;
			// Add an extra buffer, because the last buffer is an uncompressed buffer
			int numBuffers = col.getSize()/IIntMemoryBuffer.BUFFER_SIZE+1;
			bufferMetadata = new ArrayList<>(numBuffers);
			for (int i=0; i < numBuffers; i++) {
				bufferMetadata.add(new TextBufferMetadata(col, i));
			}
		}  else if (column instanceof IntListImpl) {
			IntListImpl col = (IntListImpl) column;
			bufferMetadata = new ArrayList<>(col.buffers.size());
			for (int i=0; i <col.buffers.size() ; i++) {
				bufferMetadata.add(new IntBufferMetadata(col, i));
			}
		}
	}

	public String getName() {
		return name;
	}
	
	public long getSizeInBytes() {
		return column.getSizeInBytes();
	}
	
	public DataType getType() {
		return column.getDataType();
	}
	
	public List<String> getUniqueValues() {
		return uniqueValues;
	}

	public List<BufferMetadata> getBufferMetadata() {
		return bufferMetadata;
	}

	public long getOriginalSizeInBytes() {
		return originalSizeInBytes;
	}

	public void setOriginalSizeInBytes(long originalSizeInBytes) {
		this.originalSizeInBytes = originalSizeInBytes;
	}

}

