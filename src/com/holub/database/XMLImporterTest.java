package com.holub.database;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

public class XMLImporterTest {
    public static void main(String[] args) {
        try {
            // XML ������ �����͸� ������ ���ڿ��� �����մϴ�.
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

            // ���̺� �̸� ���
            System.out.println("Table Name: " + importer.loadTableName());

            // �÷� �̸��� ���
            Iterator columnNamesIterator = importer.loadColumnNames();
            System.out.print("Column Names: ");
            while (columnNamesIterator.hasNext()) {
                System.out.print(columnNamesIterator.next() + " ");
            }
            System.out.println();

            // ��� ���
            Iterator<Map.Entry<String, String>> rowIterator = importer.loadRow();
            while (rowIterator != null) {
                System.out.println("Row: " + rowIterator.next());
                rowIterator = importer.loadRow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
