package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XMLImporter implements Table.Importer {
    private Document document;
    private NodeList rowNodes;
    private int currentRowIndex;

    public XMLImporter(Reader in) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new InputSource(in));
        document.getDocumentElement().normalize();
        rowNodes = document.getElementsByTagName("row");
        currentRowIndex = 0;
    }

    public void startTable() throws IOException {
        // No header to read for XML format
    }

    public String loadTableName() throws IOException {
        return document.getDocumentElement().getAttribute("name");
    }

    public int loadWidth() throws IOException {
        Element firstRow = (Element) rowNodes.item(0);
        return firstRow.getChildNodes().getLength();
    }

    public Iterator loadColumnNames() throws IOException {
        Element firstRow = (Element) rowNodes.item(0);
        NodeList columns = firstRow.getChildNodes();
        List<String> columnNames = new ArrayList<>();
        for (int i = 0; i < columns.getLength(); i++) {
            Node columnNode = columns.item(i);
            if (columnNode.getNodeType() == Node.ELEMENT_NODE) {
                columnNames.add(columnNode.getNodeName());
            }
        }
        return new ArrayIterator(columnNames.toArray(new String[0]));
    }

    public Iterator loadRow() throws IOException {
        if (currentRowIndex >= rowNodes.getLength()) {
            return null;
        }

        Element rowElement = (Element) rowNodes.item(currentRowIndex);
        NodeList columns = rowElement.getChildNodes();
        Map<String, String> row = new HashMap<>();

        for (int i = 0; i < columns.getLength(); i++) {
            Node columnNode = columns.item(i);
            if (columnNode.getNodeType() == Node.ELEMENT_NODE) {
                String columnName = columnNode.getNodeName();
                String columnValue = columnNode.getTextContent().trim();
                row.put(columnName, columnValue);
            }
        }

        currentRowIndex++;
        return row.entrySet().iterator();
    }

    public void endTable() throws IOException {
        // No cleanup needed for XML format
    }
}
