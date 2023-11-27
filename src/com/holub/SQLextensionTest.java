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
        // 테스트용 데이터베이스 테이블 생성
        List<Map<String, String>> testData = TestData.createTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension 객체 생성 및 테이블 추가
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // SELECT 문을 실행하고 결과를 받아옴
        SQLextension.Table result = holubSql.executeQuery("SELECT * FROM myTable");

        // 예상 결과와 실제 결과를 비교하여 테스트 수행
        assertNotNull(result);

        // 테스트용 데이터베이스 테이블에서 데이터 추출
        List<Map<String, String>> resultRows = result.getRows();
        assertEquals(testData, resultRows);

        // 테스트 결과 출력
        System.out.println("=== Select Test Result ===");
        System.out.println("Expected Result: " + testData);
        System.out.println("Actual Result  : " + resultRows);
    }

    // Distinct 테스트
    @Test
    public void testExecuteDistinct() {
        // 테스트용 데이터베이스 테이블 생성
        List<Map<String, String>> testData = TestData.createDistinctTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension 객체 생성 및 테이블 추가
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // DISTINCT 문을 실행하고 결과를 받아옴
        SQLextension.Table result = holubSql.executeQuery("SELECT DISTINCT column1, column2 FROM myTable");

        // 예상 결과와 실제 결과를 비교하여 테스트 수행
        assertNotNull(result);

        // 테스트용 데이터베이스 테이블에서 데이터 추출
        List<Map<String, String>> expectedDistinctRows = TestData.createDistinctTestData();
        List<Map<String, String>> resultRows = result.getRows();
        assertEquals(expectedDistinctRows, resultRows);

        // 테스트 결과 출력
        System.out.println("=== Distinct Test Result ===");
        System.out.println("Expected Result: " + expectedDistinctRows);
        System.out.println("Actual Result  : " + resultRows);
    }

    @Test
    public void testExecuteGroupBy() {
        // 테스트용 데이터베이스 테이블 생성
        List<Map<String, String>> testData = TestData.createGroupByTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension 객체 생성 및 테이블 추가
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // GROUP BY 문을 실행하고 결과를 받아옴
        SQLextension.Table result = holubSql.executeQuery("SELECT column1, COUNT(*) FROM myTable GROUP BY column1");

        // 예상 결과와 실제 결과를 비교하여 테스트 수행
        assertNotNull(result);

        // 테스트용 데이터베이스 테이블에서 데이터 추출
        // 예상 결과: [{column1=Value1-1, COUNT(*)=2}, {column1=Value2-1, COUNT(*)=1}]
        List<Map<String, String>> expectedGroupByRows = TestData.createGroupByTestData();
        List<Map<String, String>> resultRows = result.getRows();
        assertEquals(expectedGroupByRows, resultRows);

        // 테스트 결과 출력
        System.out.println("=== GroupBy Test Result ===");
        System.out.println("Expected Result: " + expectedGroupByRows);
        System.out.println("Actual Result  : " + resultRows);
    }

    // Order By 테스트
 // Order By 테스트
    @Test
    public void testExecuteOrderBy() {
        // 테스트용 데이터베이스 테이블 생성
        List<Map<String, String>> testData = TestData.createOrderByTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension 객체 생성 및 테이블 추가
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // ORDER BY 문을 실행하고 결과를 받아옴
        SQLextension.Table result = holubSql.executeQuery("SELECT column1, column2 FROM myTable ORDER BY column1");

        // 예상 결과와 실제 결과를 비교하여 테스트 수행
        assertNotNull(result);

        // 테스트용 데이터베이스 테이블에서 데이터 추출
        // 예상 결과: [{column1=Value1-1, column2=Value1-2}, {column1=Value2-1, column2=Value2-2}]
        List<Map<String, String>> expectedOrderByRows = TestData.createOrderByTestData();
        List<Map<String, String>> resultRows = result.getRows();
        System.out.println("=== OrderBy Test Result ===");
        System.out.println("Expected Result: " + expectedOrderByRows);
        System.out.println("Actual Result  : " + resultRows);
    }

}
