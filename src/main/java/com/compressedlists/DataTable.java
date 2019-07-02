package com.compressedlists;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.compressedlists.impl.StringBufferMetadata;
import com.compressedlists.impl.BufferMetadata;
import com.compressedlists.impl.ColumnMetadata;
import com.compressedlists.impl.IntBufferMetadata;
import com.compressedlists.impl.IntListImpl;
import com.compressedlists.impl.StringListImpl;
import com.compressedlists.impl.TextBufferMetadata;
import com.compressedlists.impl.TextListImpl;
import com.compressedlists.impl.buffer.IIntMemoryBuffer;
import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonIgnore;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;

public class DataTable {
	@JsonIgnore
	private CompressedList[] columns;
	@JsonIgnore
	private String[] headerNames;
	@JsonIgnore
	private String[] uniqueNames;

	private ColumnMetadata[] columnMetadata;
	
	public DataTable() {
		
	}
	
	public DataTable(CompressedList[] columns, String[] headerNames, String[] uniqueNames) {
		this.columns = columns;
		this.headerNames = headerNames;
		this.uniqueNames = uniqueNames;
	}

	public CompressedList[] getColumns() {
		return columns;
	}

	public String[] getHeaderNames() {
		return headerNames;
	}

	@JsonIgnore
	public String[] getUniqueNames() {
		return uniqueNames;
	}
	
	public int getNumColumns() {
		if (columns != null) {
			return columns.length;
		}
		return 0;
	}
	
	public int getBufferCount() {
		return (getNumRows()/IIntMemoryBuffer.BUFFER_SIZE)+1;
	}
	
	public int getNumRows() {
		if (columns != null && columns.length > 0) {
			return columns[0].getSize();
		}
		return 0;
	}
	
	public void writeData(File folder, CompressionType compression) throws IOException {
		// TODO write column name, type, location, into metadata.json
		// use json iterator/any from jsonitor
		folder.mkdirs();
		String fileName = "metadata.json";
		// For each buffer write for each column, compression, bufferIndex, numBytes, numRecords
		
		columnMetadata = new ColumnMetadata[columns.length];
		for (int i=0; i < columns.length; i ++) {
			columnMetadata[i] = new ColumnMetadata(headerNames[i], columns[i]);
		}
		
		// This could eventually be a Data Table.  For first pass, let's make it json
		// Column index, Buffer index, Buffer_Type, compression_type, uncompressed-size, Min, Max, #Unique Values

		int bufferCount = getBufferCount();
		// Write buffers
		int totalBytes = 0;
		for(int i=0; i < bufferCount ; i++) {
			try (RandomAccessFile file = new RandomAccessFile(new File(folder, "index_"+ i + ".dat"), "rw")) {
				file.setLength(0);//truncate current file
				int totalBufferBytes = 0;
				for (int j=0; j < getNumColumns(); j++) {
					CompressedList col = columns[j];
					int numBytesWritten = 0;
					if (col instanceof StringListImpl) {
						StringListImpl lst = (StringListImpl) col;
						if (lst.getUniqueValuesSize() > 1) {
							numBytesWritten = col.writeData(file, compression, i);
						} else {
							numBytesWritten = 0;
						}
					} else {
						numBytesWritten = col.writeData(file, compression, i);
					}
					BufferMetadata  bufMd = columnMetadata[j].getBufferMetadata().get(i);
					bufMd.setStart(totalBufferBytes);
					bufMd.setLength(numBytesWritten);
					totalBufferBytes += numBytesWritten;
				}
			}	
		}
		
		System.out.println("Serilizing Metadata");
		String json = JsonStream.serialize(this);
		try(PrintWriter jsonWriter = new PrintWriter(new File(folder, fileName))) {
			jsonWriter.write(json);
		}
	}
	
	/**This is still work in progress*/
	public static DataTable readData(File folder) throws IOException {
		// Read metadata.json
		File jsonFile = new File(folder, "metadata.json");
		
		String json = new String(Files.readAllBytes(jsonFile.toPath()), "UTF-8");
		JsonIterator iter = JsonIterator.parse(json);
		Any entireObject = iter.readAny();
		Any columns = entireObject.get("columnMetadata");
		int numColumns = entireObject.toInt("numColumns");
		int bufferCount = entireObject.toInt("bufferCount");
		int numRows = entireObject.toInt("numRows");
		
		List<String> columnNames = new ArrayList<>();
		int colNum=0;
		for (Any colMdAny: columns) {
			String colName = colMdAny.toString("name");
			columnNames.add(colName);
			DataType colType = DataType.valueOf(colMdAny.toString("type"));
//			BufferMetadata buffMd = null;
			CompressedList col = null;
			
			switch (colType) {
			case STRING:
				col = new StringListImpl();
				String[] uniqueValues = colMdAny.get("uniqueValues").asList().toArray(new String[0]);
				break;
			case TEXT:
				col = new TextListImpl();
				break;
			case INT:
				col = new IntListImpl();
				break;
			default:
				break;
			}
			
			Any buffMdListAny = colMdAny.get("Metadata");
			int row = 0;
			for (Any bufMdAny : buffMdListAny) {
				int start = bufMdAny.toInt("start");
				int length = bufMdAny.toInt("length");
				BufferMetadata bufMd = null;
				switch (colType) {
				case STRING:
					int numBitsPerRow = bufMdAny.toInt("numBitsPerRow");
					bufMd = new StringBufferMetadata((StringListImpl)col, row);
//					((StringBufferMetadata) bufMd).set
					break;
				case TEXT:
					bufMd = new TextBufferMetadata((TextListImpl)col, row);
					
					break;

				case INT:
					bufMd = new IntBufferMetadata((IntListImpl)col, row);
					break;
				default:
					break;
				}
				row ++;
			}
			colNum++;
		}
		

		DataTable table = new DataTable();
		return table;
		
		
//		int bufferCount = getNumRows();
//		for(int i=0; i < bufferCount ; i++) {
//			CompressedList col = columns[i];
////			col.readData(folder, compression, bufferIndex, numBytes, numRecords);
//		}
		
	}

	
}
