package com.holub.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import com.holub.database.Table.Exporter;

public class HTMLExporter implements Exporter {
	private final Writer out;
	private String tableName;
	private int width;
	private int height;
	private String title;
	public HTMLExporter(Writer out)
	{
		this.out = out;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Override
	public void startTable() throws IOException {
		out.write("<html><body><table border='1'>\n");
	}

	@Override
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
		this.tableName = tableName;
		this.width = width;
		this.height = height;
		
		//out.write("<table border=1>\n");
		out.write(tableName == null ? "<anonymous>" : tableName);
		out.write("\n");
		
		out.write("<tr>");
		while(columnNames.hasNext())
		{
			out.write("<th>");
			out.write(columnNames.next().toString());
			out.write("</th>");
		}
		out.write("</tr>\n");

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
			out.write("</tr>\n");
		}
	}

	@Override
	public void endTable() throws IOException {
		out.write("</table></body></html>\n");
	}
	
	
}
