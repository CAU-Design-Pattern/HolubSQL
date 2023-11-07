package com.holub.database;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import com.holub.database.Table.Exporter;

public class HTMLExporter implements Exporter {
	private final Writer out;
	private String tableName;
	private int width;
	private int height;
	public HTMLExporter(Writer out)
	{
		this.out = out;
	}
	@Override
	public void startTable() throws IOException {
		out.write("<html><body><table>");
	}

	@Override
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
		this.tableName = tableName;
		this.width = width;
		this.height = height;
		
		out.write("<table border=1>");
		out.write(tableName == null ? "<anonymous>" : tableName);
		
		out.write("<tr>");
		while(columnNames.hasNext())
		{
			out.write("<th>");
			out.write(columnNames.next().toString());
			out.write("</th>");
		}
		out.write("</tr>");

	}

	@Override
	public void storeRow(Iterator data) throws IOException {
		for(int i=0;(i<height)&&data.hasNext();i++)
		{
			out.write("<tr>");
			for(int j=0;(j<width)&&data.hasNext();j++)
			{
				out.write("<td>");
				out.write(data.next().toString());
				out.write("</td>");
			}
			out.write("</tr>");
		}
	}

	@Override
	public void endTable() throws IOException {
		out.write("</table></body></html>");
	}
	
	public final static class Test{
		
		public static void main(String[] args)
		{
			try {
				new Test().test();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		public void test() throws IOException
		{
			Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
			people.insert(new Object[] { "Holub", "Allen", "1" });
			people.insert(new Object[] { "Flintstone", "Wilma", "2" });
			people.insert(new Object[] { "New String", "Hello", "2" });
			people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });

			Writer out = new FileWriter("src\\com\\holub\\database\\people.html");
			HTMLExporter exporter = new HTMLExporter(out);
			people.export(exporter);
			out.close();
		}
	}
}
