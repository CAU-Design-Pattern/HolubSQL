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

        // QueryStrategy 설정
        QueryStrategy selectQuery = new SelectQuery();
        holubSql.setQueryStrategy(selectQuery);

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

    @Test
    public void testExecuteDistinct() {
        // Distinct 테스트 추가
        // 테스트용 데이터베이스 테이블 생성
        List<Map<String, String>> testData = TestData.createDistinctTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension 객체 생성 및 테이블 추가
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // QueryStrategy 설정
        QueryStrategy distinctQuery = new DistinctQuery();
        holubSql.setQueryStrategy(distinctQuery);

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
        // Group By 테스트 추가
        // 테스트용 데이터베이스 테이블 생성
        List<Map<String, String>> testData = TestData.createGroupByTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension 객체 생성 및 테이블 추가
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // QueryStrategy 설정
        QueryStrategy groupByQuery = new GroupByQuery();
        holubSql.setQueryStrategy(groupByQuery);

        // GROUP BY 문을 실행하고 결과를 받아옴
        SQLextension.Table result = holubSql.executeQuery("SELECT column1, COUNT(*) FROM myTable GROUP BY column1");

        // 예상 결과와 실제 결과를 비교하여 테스트 수행
        assertNotNull(result);

        // 테스트용 데이터베이스 테이블에서 데이터 추출
        // 여기서는 단순히 그룹화된 결과를 리턴하는 것으로 가정
        List<Map<String, String>> resultRows = result.getRows();
        System.out.println("=== GroupBy Test Result ===");
        System.out.println("Expected Result: " + testData);
        System.out.println("Actual Result  : " + resultRows);
    }

    @Test
    public void testExecuteOrderBy() {
        // Order By 테스트 추가
        // 테스트용 데이터베이스 테이블 생성
        List<Map<String, String>> testData = TestData.createOrderByTestData();
        SQLextension.Table table = new SQLextension.Table(testData);

        // SQLextension 객체 생성 및 테이블 추가
        SQLextension holubSql = new SQLextension();
        holubSql.addTable(table);

        // QueryStrategy 설정
        QueryStrategy orderByQuery = new OrderByQuery();
        holubSql.setQueryStrategy(orderByQuery);

        // ORDER BY 문을 실행하고 결과를 받아옴
        SQLextension.Table result = holubSql.executeQuery("SELECT column1, column2 FROM myTable ORDER BY column1");

        // 예상 결과와 실제 결과를 비교하여 테스트 수행
        assertNotNull(result);

        // 테스트용 데이터베이스 테이블에서 데이터 추출
        List<Map<String, String>> expectedOrderByRows = TestData.createOrderByTestData();
        List<Map<String, String>> resultRows = result.getRows();
        System.out.println("=== OrderBy Test Result ===");
        System.out.println("Expected Result: " + expectedOrderByRows);
        System.out.println("Actual Result  : " + resultRows);
    }
}
