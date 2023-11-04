package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class XMLExporter implements Table.Exporter {
	private final Writer out;
	private String tableName;
	private int width;
	private String[] columnNames;

	public XMLExporter(Writer out) {
		this.out = out;
	}

	@Override
	public void startTable() throws IOException {
		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	}

	@Override
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
		this.tableName = tableName == null ? "anonymous" : tableName;
		this.width = width;
		this.columnNames = new String[width];
		int i = 0;
		while (columnNames.hasNext())
			this.columnNames[i++] = (String) columnNames.next();
		out.write("<" + tableName + ">\n");
	}

	@Override
	public void storeRow(Iterator data) throws IOException {
		out.write("\t<row>\n");
		int i = 0;
		while (data.hasNext()) {
			Object datum = data.next();
			out.write("\t\t");
			out.write("<" + columnNames[i] + ">");
			if (datum != null)
				out.write(datum.toString());
			else
				out.write("null");
			out.write("</" + columnNames[i] + ">\n");
			i++;
		}
		out.write("\t</row>\n");
	}

	@Override
	public void endTable() throws IOException {
		out.write("</" + tableName + ">\n");
	}
}
