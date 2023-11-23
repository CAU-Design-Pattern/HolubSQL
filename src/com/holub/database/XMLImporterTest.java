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

            // 문자열을 Reader로 변환하여 XMLImporter 객체를 생성합니다.
            Reader reader = new StringReader(xmlData);
            XMLImporter importer = new XMLImporter(reader);

            // 테이블 이름 검증
            assertEquals("SampleTable", importer.loadTableName());

            // 컬럼 이름들 검증
            Iterator columnNamesIterator = importer.loadColumnNames();
            assertEquals("column1", columnNamesIterator.next());
            assertEquals("column2", columnNamesIterator.next());
            assertFalse(columnNamesIterator.hasNext()); // 더 이상 요소가 없어야 함

            // 첫 번째 행 검증
            Iterator<Map.Entry<String, String>> rowIterator = importer.loadRow();
            assertEquals("column1=Value1-1", rowIterator.next().toString());
            assertEquals("column2=Value1-2", rowIterator.next().toString());

            // 두 번째 행 검증
            rowIterator = importer.loadRow();
            assertEquals("column1=Value2-1", rowIterator.next().toString());
            assertEquals("column2=Value2-2", rowIterator.next().toString());

            // 더 이상 행이 없는지 검증
            assertNull(importer.loadRow());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}