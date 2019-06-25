package com.compressedlists;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import com.compressedlists.impl.IntListImpl;
import com.compressedlists.impl.StringListImpl;
import com.compressedlists.impl.TextListImpl;

import au.com.bytecode.opencsv.CSVReader;

public class FileUtils {
	
	public static DataTable readFile(File file, Properties props) throws IOException {
		DataTable dataTable = null;
		CompressedList[] columns = null;
		DataType[] columntypes;
				
		long start = System.currentTimeMillis();
		int count = 0;
		
		char delimiter = ',';
		if (file.getName().endsWith(".tsv") || file.getName().endsWith(".txt")) {
			delimiter = '\t';
		} else if (file.getName().endsWith(".csv")) {
			delimiter = ',';
		} else {
			delimiter = '|';
		}
		
		try (FileReader reader = new FileReader(file);
				CSVReader csvReader = new CSVReader(reader, delimiter, '\0')) {
			String[] header = csvReader.readNext();
			
			columntypes = new DataType[header.length];
			System.out.println(Arrays.toString(header));
			long timeParsing = 0;
			if(header != null) {
				
				
				columns = new CompressedList[header.length];
				dataTable = new DataTable(columns, header, header);
				for (int i=0; i< columns.length; i++) {
					String key = header[i] + "." + "type"; 
					String type = props.getProperty(key);
					if ( type == null || type.length() == 0) {
						columns[i] = new StringListImpl();
					} else {
						DataType columnType = DataType.valueOf(type);
						switch (columnType) {
						case INT:
							columns[i] = new IntListImpl();
							break;

						default:
							columns[i] = new StringListImpl();
							break;
						}
					}
				}
				
				String[] nextLine = csvReader.readNext();
				

				while ((nextLine = csvReader.readNext()) != null) {
					long before = System.nanoTime();
					for (int i=0; i< header.length; i++) {
						if (i < nextLine.length) {
							columns[i].addValue(nextLine[i]);
						} else {
							columns[i].addValue("");
						}
						
						if (columns[i].hasMaxUniqueValues()) {
							switch (columns[i].getDataType()) {
							case TEXT:
								TextList col = (TextList) columns[i];
								// Convert to Compressed column
								columns[i] = new TextListImpl(col);
								break;

							default:
								break;
							}
							
						}
					}
					timeParsing += (System.nanoTime() - before);
					count++;
				}
			}

			System.out.println("Adding To Data Structures: "  + timeParsing/1000000/1000.0 + " seconds");
			for (int i=0; i < columns.length; i++) {
				CompressedList col = columns[i];
				if (col instanceof StringList) {
					System.out.println(header[i] + ": " + col.getDataType() + ": " +
							((StringList)col).getUniqueValuesSize() + ": "+ 
							col.getTimeProcessed()/1000.0 + " seconds");
				} else {
					System.out.println(header[i] + ": " + col.getDataType() + ": -- : "+ 
							col.getTimeProcessed()/1000.0 + " seconds");
				}
			}
		} 
		System.gc();
		System.out.println("Loaded " + count + " rows in "+ (System.currentTimeMillis()-start)/1000.0 + " s");
		
		return dataTable;
	}
}
