package com.holub.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {
    public static List<Map<String, String>> createTestData() {
        List<Map<String, String>> testData = new ArrayList<>();

        // �׽�Ʈ ������ �߰�
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row2);

        // �ʿ��� ��ŭ ������ �߰�...

        return testData;
    }

    public static List<Map<String, String>> createSelectTestData() {
        List<Map<String, String>> testData = new ArrayList<>();

        // �׽�Ʈ ������ �߰�
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row2);

        // �ʿ��� ��ŭ ������ �߰�...

        return testData;
    }

    public static List<Map<String, String>> createDistinctTestData() {
        // DISTINCT �׽�Ʈ�� ���� ������ ����
        // �ߺ��� �����Ͱ� ���� �����͸� �����Ͽ� ��ȯ
        List<Map<String, String>> testData = new ArrayList<>();

        // �׽�Ʈ ������ �߰�
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row2);

        // �ʿ��� ��ŭ ������ �߰�...

        return testData;
    }

    public static List<Map<String, String>> createGroupByTestData() {
        // GROUP BY �׽�Ʈ�� ���� ������ ����
        // �׷�ȭ�� �����͸� �����Ͽ� ��ȯ
        List<Map<String, String>> testData = new ArrayList<>();

        // �׽�Ʈ ������ �߰� (���⼭�� �׷�ȭ�� �÷��� column1�� ���)
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("COUNT(*)", "1");
        // �߰����� �÷� ������ �߰�...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("COUNT(*)", "1");
        // �߰����� �÷� ������ �߰�...
        testData.add(row2);

        // �ʿ��� ��ŭ ������ �߰�...

        return testData;
    }

    public static List<Map<String, String>> createOrderByTestData() {
        // ORDER BY �׽�Ʈ�� ���� ������ ����
        // ���ĵ� �����͸� �����Ͽ� ��ȯ
        List<Map<String, String>> testData = new ArrayList<>();

        // �׽�Ʈ ������ �߰� (���⼭�� column1�� �������� ����)
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // �߰����� �÷� ������ �߰�...
        testData.add(row2);

        // �ʿ��� ��ŭ ������ �߰�...

        return testData;
    }

    // �ٸ� �׽�Ʈ ������ ���� �޼����...
}
