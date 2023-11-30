package com.holub.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {
    public static List<Map<String, String>> createTestData() {
        List<Map<String, String>> testData = new ArrayList<>();

        // 테스트 데이터 추가
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row2);

        // 필요한 만큼 데이터 추가...

        return testData;
    }

    public static List<Map<String, String>> createSelectTestData() {
        List<Map<String, String>> testData = new ArrayList<>();

        // 테스트 데이터 추가
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row2);

        // 필요한 만큼 데이터 추가...

        return testData;
    }

    public static List<Map<String, String>> createDistinctTestData() {
        // DISTINCT 테스트에 사용될 데이터 생성
        // 중복된 데이터가 없는 데이터를 생성하여 반환
        List<Map<String, String>> testData = new ArrayList<>();

        // 테스트 데이터 추가
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row2);

        // 필요한 만큼 데이터 추가...

        return testData;
    }

    public static List<Map<String, String>> createGroupByTestData() {
        // GROUP BY 테스트에 사용될 데이터 생성
        // 그룹화된 데이터를 생성하여 반환
        List<Map<String, String>> testData = new ArrayList<>();

        // 테스트 데이터 추가 (여기서는 그룹화할 컬럼이 column1인 경우)
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("COUNT(*)", "1");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("COUNT(*)", "1");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row2);

        // 필요한 만큼 데이터 추가...

        return testData;
    }

    public static List<Map<String, String>> createOrderByTestData() {
        // ORDER BY 테스트에 사용될 데이터 생성
        // 정렬된 데이터를 생성하여 반환
        List<Map<String, String>> testData = new ArrayList<>();

        // 테스트 데이터 추가 (여기서는 column1을 기준으로 정렬)
        Map<String, String> row1 = new HashMap<>();
        row1.put("column1", "Value1-1");
        row1.put("column2", "Value1-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row1);

        Map<String, String> row2 = new HashMap<>();
        row2.put("column1", "Value2-1");
        row2.put("column2", "Value2-2");
        // 추가적인 컬럼 데이터 추가...
        testData.add(row2);

        // 필요한 만큼 데이터 추가...

        return testData;
    }

    // 다른 테스트 데이터 생성 메서드들...
}
