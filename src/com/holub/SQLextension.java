package com.holub.database;

import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;



class SQLextension {
    private List<Table> tables;

    public SQLextension() {
        this.tables = new ArrayList<>();
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public Table executeQuery(String query) {
        if (query.contains("SELECT") && query.contains("FROM")) {
            List<String> selectedColumns = extractColumns(query);

            if (query.contains("DISTINCT")) {
                return executeDistinct(selectedColumns);
            } else if (query.contains("GROUP BY")) {
                String groupByColumn = extractGroupByColumn(query);
                return executeGroupBy(selectedColumns, groupByColumn);
            } else if (query.contains("ORDER BY")) {
                String orderByColumn = extractOrderByColumn(query);
                return executeOrderBy(selectedColumns, orderByColumn);
            } else {
                return executeSelect(selectedColumns);
            }
        }

        // 간단한 처리 로직을 넣어줄 것
        return new Table(Collections.emptyList()); // 빈 리스트로 초기화
    }

    private List<String> extractColumns(String query) {
        // 쿼리에서 선택된 열을 추출하는 로직
        // 여기서는 "SELECT" 다음의 내용을 추출
        int startIndex = query.indexOf("SELECT") + 6;
        int endIndex = query.indexOf("FROM");
        String columnsString = query.substring(startIndex, endIndex).trim();
        if ("*".equals(columnsString)) {
            // "*"인 경우 모든 열을 선택
            return tables.get(0).getRows().isEmpty() ?
                    Collections.emptyList() :
                    new ArrayList<>(tables.get(0).getRows().get(0).keySet());
        } else {
            return Arrays.asList(columnsString.split(","));
        }
    }


    private String extractGroupByColumn(String query) {
        // GROUP BY 절에서 그룹화할 열을 추출하는 로직
        // 여기서는 간략화를 위해 "GROUP BY" 구문 뒤에 오는 열을 추출한다고 가정
        int groupByIndex = query.indexOf("GROUP BY");
        return query.substring(groupByIndex + 8).trim();
    }

    private String extractOrderByColumn(String query) {
        // ORDER BY 절에서 정렬할 열을 추출하는 로직
        // 여기서는 간략화를 위해 "ORDER BY" 구문 뒤에 오는 열을 추출한다고 가정
        int orderByIndex = query.indexOf("ORDER BY");
        return query.substring(orderByIndex + 8).trim();
    }

    private Table executeDistinct(List<String> selectedColumns) {
        // DISTINCT 처리 로직
        // 중복을 제거하여 결과 반환
        Table table = tables.get(0); // 여기서는 첫 번째 테이블을 사용한다고 가정
        return table.distinct(selectedColumns);
    }

 // executeGroupBy 메서드 수정
    private Table executeGroupBy(List<String> selectedColumns, String groupByColumn) {
        // GROUP BY 처리 로직
        // 그룹화된 결과 반환
        Table table = tables.get(0); // 여기서는 첫 번째 테이블을 사용한다고 가정
        Map<String, List<Map<String, String>>> groupedData = table.getRows().stream()
                .collect(Collectors.groupingBy(row -> row.get(groupByColumn)));

        List<Map<String, String>> resultRows = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, String>>> entry : groupedData.entrySet()) {
            Map<String, String> groupRow = new HashMap<>();
            groupRow.put(groupByColumn, entry.getKey());
            groupRow.put("COUNT(*)", String.valueOf(entry.getValue().size()));
            resultRows.add(groupRow);
        }

        return new Table(resultRows);
    }


    private Table executeOrderBy(List<String> selectedColumns, String orderByColumn) {
        // ORDER BY 처리 로직
        // 정렬된 결과 반환
        Table table = tables.get(0); // 여기서는 첫 번째 테이블을 사용한다고 가정
        return table.orderBy(selectedColumns, orderByColumn);
    }

 // executeSelect 메서드 수정
    private Table executeSelect(List<String> selectedColumns) {
        // SELECT 처리 로직
        // 선택된 열을 반환
        Table table = tables.get(0); // 여기서는 첫 번째 테이블을 사용한다고 가정
        List<Map<String, String>> selectedRows = table.getRows().stream()
                .map(row -> {
                    if (selectedColumns.isEmpty()) {
                        // 선택된 열이 없으면 모든 열 선택
                        return new HashMap<>(row);
                    } else {
                        // 선택된 열만 선택
                        return selectedColumns.stream()
                                .filter(row::containsKey)
                                .collect(Collectors.toMap(column -> column, row::get));
                    }
                })
                .collect(Collectors.toList());

        return new Table(selectedRows);
    }


    // Table 클래스와 관련 메서드들
    static class Table {
        private List<Map<String, String>> rows;

        public Table(List<Map<String, String>> rows) {
            this.rows = rows;
        }

        public Table distinct(List<String> selectedColumns) {
            // DISTINCT 처리 로직
            // 중복을 제거하여 결과 반환
            List<Map<String, String>> distinctRows = rows.stream()
                    .distinct()
                    .collect(Collectors.toList());
            return new Table(distinctRows);
        }

        public Table groupBy(List<String> selectedColumns, String groupByColumn) {
            // GROUP BY 처리 로직
            // 그룹화된 결과 반환
            // 여기서는 단순히 그룹화된 결과를 리턴하는 것으로 가정
            return new Table(rows); // Placeholder logic
        }

        public Table orderBy(List<String> selectedColumns, String orderByColumn) {
            // ORDER BY 처리 로직
            // 정렬된 결과 반환
            List<Map<String, String>> sortedRows = rows.stream()
                    .sorted((r1, r2) -> r1.get(orderByColumn).compareTo(r2.get(orderByColumn)))
                    .collect(Collectors.toList());
            return new Table(sortedRows);
        }

        public Table select(List<String> selectedColumns) {
            // SELECT 처리 로직
            // 선택된 열을 반환
            // 여기서는 모든 열을 선택하는 것으로 가정
            return new Table(rows);
        }

        public List<Map<String, String>> getRows() {
            return rows;
        }
    }
}
