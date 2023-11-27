package com.holub.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SQLextensionTest {

    @Test
    public void testExecuteSelect() {
        // �׽�Ʈ�� �����ͺ��̽� ���̺� ����
        List<Map<String, String>> testData = TestData.createTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension ��ü ���� �� ���̺� �߰�
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // SELECT ���� �����ϰ� ����� �޾ƿ�
        SQLextension.Table result = holubSql.executeQuery("SELECT * FROM myTable");

        // ���� ����� ���� ����� ���Ͽ� �׽�Ʈ ����
        assertNotNull(result);

        // �׽�Ʈ�� �����ͺ��̽� ���̺��� ������ ����
        List<Map<String, String>> resultRows = result.getRows();
        assertEquals(testData, resultRows);

        // �׽�Ʈ ��� ���
        System.out.println("=== Select Test Result ===");
        System.out.println("Expected Result: " + testData);
        System.out.println("Actual Result  : " + resultRows);
    }

    // Distinct �׽�Ʈ
    @Test
    public void testExecuteDistinct() {
        // �׽�Ʈ�� �����ͺ��̽� ���̺� ����
        List<Map<String, String>> testData = TestData.createDistinctTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension ��ü ���� �� ���̺� �߰�
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // DISTINCT ���� �����ϰ� ����� �޾ƿ�
        SQLextension.Table result = holubSql.executeQuery("SELECT DISTINCT column1, column2 FROM myTable");

        // ���� ����� ���� ����� ���Ͽ� �׽�Ʈ ����
        assertNotNull(result);

        // �׽�Ʈ�� �����ͺ��̽� ���̺��� ������ ����
        List<Map<String, String>> expectedDistinctRows = TestData.createDistinctTestData();
        List<Map<String, String>> resultRows = result.getRows();
        assertEquals(expectedDistinctRows, resultRows);

        // �׽�Ʈ ��� ���
        System.out.println("=== Distinct Test Result ===");
        System.out.println("Expected Result: " + expectedDistinctRows);
        System.out.println("Actual Result  : " + resultRows);
    }

    @Test
    public void testExecuteGroupBy() {
        // �׽�Ʈ�� �����ͺ��̽� ���̺� ����
        List<Map<String, String>> testData = TestData.createGroupByTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension ��ü ���� �� ���̺� �߰�
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // GROUP BY ���� �����ϰ� ����� �޾ƿ�
        SQLextension.Table result = holubSql.executeQuery("SELECT column1, COUNT(*) FROM myTable GROUP BY column1");

        // ���� ����� ���� ����� ���Ͽ� �׽�Ʈ ����
        assertNotNull(result);

        // �׽�Ʈ�� �����ͺ��̽� ���̺��� ������ ����
        // ���� ���: [{column1=Value1-1, COUNT(*)=2}, {column1=Value2-1, COUNT(*)=1}]
        List<Map<String, String>> expectedGroupByRows = TestData.createGroupByTestData();
        List<Map<String, String>> resultRows = result.getRows();
        assertEquals(expectedGroupByRows, resultRows);

        // �׽�Ʈ ��� ���
        System.out.println("=== GroupBy Test Result ===");
        System.out.println("Expected Result: " + expectedGroupByRows);
        System.out.println("Actual Result  : " + resultRows);
    }

    // Order By �׽�Ʈ
 // Order By �׽�Ʈ
    @Test
    public void testExecuteOrderBy() {
        // �׽�Ʈ�� �����ͺ��̽� ���̺� ����
        List<Map<String, String>> testData = TestData.createOrderByTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension ��ü ���� �� ���̺� �߰�
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // ORDER BY ���� �����ϰ� ����� �޾ƿ�
        SQLextension.Table result = holubSql.executeQuery("SELECT column1, column2 FROM myTable ORDER BY column1");

        // ���� ����� ���� ����� ���Ͽ� �׽�Ʈ ����
        assertNotNull(result);

        // �׽�Ʈ�� �����ͺ��̽� ���̺��� ������ ����
        // ���� ���: [{column1=Value1-1, column2=Value1-2}, {column1=Value2-1, column2=Value2-2}]
        List<Map<String, String>> expectedOrderByRows = TestData.createOrderByTestData();
        List<Map<String, String>> resultRows = result.getRows();
        System.out.println("=== OrderBy Test Result ===");
        System.out.println("Expected Result: " + expectedOrderByRows);
        System.out.println("Actual Result  : " + resultRows);
    }

}
