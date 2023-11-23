package com.holub.database;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class XMLImporterTest {

    @Test
    public void testXMLImporter() {
        try {
            String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<table name=\"SampleTable\">" +
                    "  <row>" +
                    "    <column1>Value1-1</column1>" +
                    "    <column2>Value1-2</column2>" +
                    "  </row>" +
                    "  <row>" +
                    "    <column1>Value2-1</column1>" +
                    "    <column2>Value2-2</column2>" +
                    "  </row>" +
                    "</table>";

            // ���ڿ��� Reader�� ��ȯ�Ͽ� XMLImporter ��ü�� �����մϴ�.
            Reader reader = new StringReader(xmlData);
            XMLImporter importer = new XMLImporter(reader);

            // ���̺� �̸� ����
            assertEquals("SampleTable", importer.loadTableName());

            // �÷� �̸��� ����
            Iterator columnNamesIterator = importer.loadColumnNames();
            assertEquals("column1", columnNamesIterator.next());
            assertEquals("column2", columnNamesIterator.next());
            assertFalse(columnNamesIterator.hasNext()); // �� �̻� ��Ұ� ����� ��

            // ù ��° �� ����
            Iterator<Map.Entry<String, String>> rowIterator = importer.loadRow();
            assertEquals("column1=Value1-1", rowIterator.next().toString());
            assertEquals("column2=Value1-2", rowIterator.next().toString());

            // �� ��° �� ����
            rowIterator = importer.loadRow();
            assertEquals("column1=Value2-1", rowIterator.next().toString());
            assertEquals("column2=Value2-2", rowIterator.next().toString());

            // �� �̻� ���� ������ ����
            assertNull(importer.loadRow());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}