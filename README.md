# CompressedLists
This collection allows a developer to store strings and integers in a compressed format with minimal overhead.

Strings are fairly robust.  Integers are still a work in progress.

## Creating a StringList
```
StringList lst = new StringListImpl()
lst.addValue("Cat");
lst.addValue("Dog");
String first = lst.getValue();
```

## Converting a csv to the compressed list format:
```        
File[] inFiles = new File[inFilePaths.length];
for (int i=0; i < inFiles.length; i++) {
	inFiles[i] = new File(inFilePaths[i]);
}
DataTable table = 
		FileUtils.readFile(inFiles, new Properties(), null);
long begin = System.currentTimeMillis();
table.writeData(new File(outFolder), CompressionType.DEFAULT);
System.out.println("Wrote Data in " + (System.currentTimeMillis() - begin)/1000.0 + " s");

```

## Reading data from the compressed list format:
```
long begin = System.currentTimeMillis();
DataTable table = DataTable.readData(new File(inFolder), null);
System.out.println("Loaded table with " +  
		table.getNumRows() + " rows and " + table.getNumColumns() + " columns in "  + 
		(System.currentTimeMillis() - begin)/1000.0 + " s");  
```
