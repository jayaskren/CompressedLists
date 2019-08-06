package com.compressedlists;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

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
					
					BufferMetadata  bufMd = null;
					if (col instanceof StringListImpl) {
						bufMd = columnMetadata[j].getBufferMetadata().get(i);
						StringListImpl lst = (StringListImpl) col;
						if (lst.getUniqueValuesSize() > 1) {
							numBytesWritten = col.writeData(file, compression, i, bufMd);
						} else {
							numBytesWritten = 0;
						}
					} else if (col instanceof TextListImpl) {
						try {
							bufMd = columnMetadata[j].getBufferMetadata().get(i);
							numBytesWritten = col.writeData(file, compression, i, bufMd);
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
							throw e;
						}
					} else {
						bufMd = columnMetadata[j].getBufferMetadata().get(i);
						numBytesWritten = col.writeData(file, compression, i, bufMd);
					}
					
					bufMd.setStart(totalBufferBytes);
					bufMd.setNumRows(col.getBufferSize(i));
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
	public static DataTable readData(File folder, IUpdatable progress) throws IOException {
		// Read metadata.json
		long begin = System.currentTimeMillis();
		File jsonFile = new File(folder, "metadata.json");
		
		String json = new String(Files.readAllBytes(jsonFile.toPath()), "UTF-8");
		JsonIterator iter = JsonIterator.parse(json);
		Any entireObject = iter.readAny();
		Any columnsAny = entireObject.get("columnMetadata");
		int numColumns = entireObject.toInt("numColumns");
//		int bufferCount = entireObject.toInt("bufferCount");
//		int totalNumRows = entireObject.toInt("numRows");
		CompressedList[] columns = new CompressedList[numColumns];
		List<String> columnNames = new ArrayList<>();
//		List<CompressedList> columns = new ArrayList<>();
		int colNum=0;
		if (progress != null) {
			progress.setMax(numColumns);
		}
		for (Any colMdAny: columnsAny) {
			String colName = colMdAny.toString("name");
			columnNames.add(colName);
			DataType colType = DataType.valueOf(colMdAny.toString("type"));
			colMdAny.toString("Compression");
			CompressedList col = null;
			int bufCount = 0;
			Any bufMdAny = colMdAny.get("bufferMetadata");
			
			ListIterator<Any> bufIter = bufMdAny.asList().listIterator();
			
			switch (colType) {
			case STRING:
				
				Iterator<Any> tempUniqueValusIter = colMdAny.get("uniqueValues").asList().iterator();
				List<StringBufferMetadata> stringBufDataList = new ArrayList<>();
				final List<String> tempUniqueValuesList= new ArrayList<>();
				
				tempUniqueValusIter.forEachRemaining(new Consumer<Any>() {
					@Override
					public void accept(Any t) {
						tempUniqueValuesList.add(t.toString());
					}
				});
				
				
				while (bufIter.hasNext()) {
					Any next = bufIter.next();
					int numBitsPerRow = next.toInt("numBitsPerRow");
					int startPos = next.toInt("start");
					int numRows = next.toInt("numRows");
					StringBufferMetadata md = new StringBufferMetadata(bufCount, startPos, numRows, numBitsPerRow);
					
//						IIntMemoryBuffer buffer = Factor
					stringBufDataList.add(md);
					bufCount ++;
				}
				col = new StringListImpl(tempUniqueValuesList, stringBufDataList);
				
				for (int i=0; i < stringBufDataList.size(); i++) {
					try (RandomAccessFile file = new RandomAccessFile(new File(folder, "index_"+ i + ".dat"), "r")) {
						StringBufferMetadata md = stringBufDataList.get(i);
						file.seek(md.getStart());
						col.readData(file, CompressionType.DEFAULT, i, md.getNumRows()*md.getNumBitsPerRow()/8, md.getNumRows(), md);
					}
				}
				
				break;
			case TEXT:
				List<TextBufferMetadata> textBufDataList = new ArrayList<>();
				while (bufIter.hasNext()) {
					Any next = bufIter.next();
					int uncompressedBytes = next.toInt("uncompressedByteSize");
					int compressedBytes = next.toInt("compressedByteSize");
					int startPos = next.toInt("start");
					int numRows = next.toInt("numRows");
					TextBufferMetadata md = new TextBufferMetadata(bufCount, startPos, numRows, uncompressedBytes, compressedBytes);
					
					textBufDataList.add(md);
					bufCount ++;
				}
				col = new TextListImpl(textBufDataList);
				
				for (int i=0; i < textBufDataList.size(); i++) {
					try (RandomAccessFile file = new RandomAccessFile(new File(folder, "index_"+ i + ".dat"), "r")) {
						TextBufferMetadata md = textBufDataList.get(i);
						file.seek(md.getStart());
						col.readData(file, CompressionType.DEFAULT, i, md.getCompressedByteSize(), md.getNumRows(), md);
					}
				}
				break;
			case INT:
				col = new IntListImpl();
				break;
			default:
				break;
			}
			columns[colNum] = col;
			colNum++;
			if (progress != null) {
				progress.updateProgress(colNum, "Loading Column " + colNum+1);
			}
		}
		
		String[] header = columnNames.toArray(new String[columnNames.size()]);
		DataTable table = new DataTable(columns, header, header);
		System.out.println("Loaded in " + (System.currentTimeMillis() - begin) + "ms");
		if (progress != null) {
			progress.finish();
		}
		return table;		
	}

	
}
