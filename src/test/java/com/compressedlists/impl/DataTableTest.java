package com.compressedlists.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.compressedlists.CompressedList;
import com.compressedlists.CompressionType;
import com.compressedlists.DataTable;
import com.compressedlists.FileUtils;

public class DataTableTest {

	@Test
	public void testReadWriteSimple() {
		try {
			DataTable dataTable = FileUtils.readFile(new File(DataTableTest.class.getResource("simple.csv").getFile()), new Properties());
			assertEquals(4, dataTable.getNumColumns());
			assertEquals(20, dataTable.getNumRows());
			
			dataTable.writeData(new File("/tmp/test/"), CompressionType.DEFAULT);
			DataTable newTable = DataTable.readData(new File("/tmp/test"), null);
			assertEquals(4, newTable.getNumColumns());
			assertEquals(20, newTable.getNumRows());

			for (int i=0; i < 20; i++) {
				assertEquals("Error when row=" + i + " and column=0", Integer.toString(i+1), newTable.getColumns()[0].getValueDisplay(i));
			}
			
			for (int i=0; i < 20; i++) {
				assertEquals("Error when row=" + i + " and column=1", Integer.toString((i/4)+1), newTable.getColumns()[1].getValueDisplay(i));
			}
			
			for (int i=0; i < 20; i++) {
				assertEquals("Error when row=" + i + " and column=2", "1", newTable.getColumns()[2].getValueDisplay(i));
			}
			
			for (int i=0; i < 20; i++) {
				assertEquals("Error when row=" + i + " and column=2", Integer.toString(20-i), newTable.getColumns()[3].getValueDisplay(i));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testReadWriteMultipleBuffers() throws IOException {
		StringListImpl col1 = new StringListImpl();
		StringListImpl col2 = new StringListImpl();
		StringListImpl col3 = new StringListImpl();
		StringListImpl col4 = new StringListImpl();
		int numRows = 65535;
		
		for (int i=0; i < numRows; i++) {
			col1.addValue(Integer.toString(i%300));
			col2.addValue("Foo");
			col3.addValue(Integer.toString(i%30000));
			col4.addValue(Integer.toString(i));
		}
		String[] header = new String[] {"col1", "col2", "col3", "col4"};
		DataTable table = new DataTable(new CompressedList[] {col1, col2, col3, col4}, header, header);
		assertEquals(4, table.getNumColumns());
		assertEquals(numRows, table.getNumRows());
		
		for (int i=0; i < numRows; i++) {
			assertEquals("Error when row=" + i + " and column=0", Integer.toString(i%300), table.getColumns()[0].getValueDisplay(i));
			assertEquals("Error when row=" + i + " and column=1", "Foo", table.getColumns()[1].getValueDisplay(i));
			assertEquals("Error when row=" + i + " and column=2", Integer.toString(i%30000), table.getColumns()[2].getValueDisplay(i));
			assertEquals("Error when row=" + i + " and column=3", Integer.toString(i), table.getColumns()[3].getValueDisplay(i));
		}
		
		table.writeData(new File("/tmp/test2/"), CompressionType.DEFAULT);
		DataTable newTable = DataTable.readData(new File("/tmp/test2"), null);
		assertEquals(4, newTable.getNumColumns());
		assertEquals(numRows, newTable.getNumRows());
		 
		for (int i=0; i < numRows; i++) { 
			assertEquals("Error when row=" + i + " and column=0", Integer.toString(i%300), ((StringListImpl)newTable.getColumns()[0]).getValue(i));
			assertEquals("Error when row=" + i + " and column=1", "Foo", newTable.getColumns()[1].getValueDisplay(i));
			assertEquals("Error when row=" + i + " and column=2", Integer.toString(i%30000), newTable.getColumns()[2].getValueDisplay(i));
			assertEquals("Error when row=" + i + " and column=3", Integer.toString(i), newTable.getColumns()[3].getValueDisplay(i));
		}
		
	}

}
