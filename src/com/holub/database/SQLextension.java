package com.holub.database;

import java.util.*;
import java.util.stream.Collectors;

public interface QueryStrategy {
    
    SQLextension.Table executeQuery(List<String> selectedColumns, SQLextension.Table table);
}

class DistinctQuery implements QueryStrategy {
    @Override
    public SQLextension.Table executeQuery(List<String> selectedColumns, SQLextension.Table table) {
        return table.distinct(selectedColumns);
    }
}

class GroupByQuery implements QueryStrategy {
    @Override
    public SQLextension.Table executeQuery(List<String> selectedColumns, SQLextension.Table table) {
        return table.groupBy(selectedColumns.get(0));
    }
}

class OrderByQuery implements QueryStrategy {
    @Override
    public SQLextension.Table executeQuery(List<String> selectedColumns, SQLextension.Table table) {
        return table.orderBy(selectedColumns.get(0));
    }
}

class SelectQuery implements QueryStrategy {
    @Override
    public SQLextension.Table executeQuery(List<String> selectedColumns, SQLextension.Table table) {
        return table.select(selectedColumns);
    }
}

public class SQLextension {
    private List<Table> tables;
    private QueryStrategy queryStrategy;

    public SQLextension() {
        this.tables = new ArrayList<>();
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public void setQueryStrategy(QueryStrategy queryStrategy) {
        this.queryStrategy = queryStrategy;
    }

    public Table executeQuery(String query) {
        if (query.contains("SELECT") && query.contains("FROM")) {
            List<String> selectedColumns = extractColumns(query);
            if (queryStrategy != null) {
                // 여기서 SQLextension.Table을 그냥 Table로 변경
                return queryStrategy.executeQuery(selectedColumns, tables.get(0));
            } else {
                throw new IllegalStateException("Query strategy is not set.");
            }
        }

        return new Table(Collections.emptyList());
    }

    private List<String> extractColumns(String query) {
        int startIndex = query.indexOf("SELECT") + 6;
        int endIndex = query.indexOf("FROM");
        String columnsString = query.substring(startIndex, endIndex).trim();
        if ("*".equals(columnsString)) {
            return tables.get(0).getRows().isEmpty() ?
                    Collections.emptyList() :
                    new ArrayList<>(tables.get(0).getRows().get(0).keySet());
        } else {
            return Arrays.asList(columnsString.split(","));
        }
    }

    // Nested Table class
    public static class Table {
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

        public Table groupBy(String groupByColumn) {
            // GROUP BY 처리 로직
            // 그룹화된 결과 반환
            // 여기서는 단순히 그룹화된 결과를 리턴하는 것
            return new Table(rows); // Placeholder logic
        }

        public Table orderBy(String orderByColumn) {
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
