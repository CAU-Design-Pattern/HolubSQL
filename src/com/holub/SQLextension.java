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

        // ������ ó�� ������ �־��� ��
        return new Table(Collections.emptyList()); // �� ����Ʈ�� �ʱ�ȭ
    }

    private List<String> extractColumns(String query) {
        // �������� ���õ� ���� �����ϴ� ����
        // ���⼭�� "SELECT" ������ ������ ����
        int startIndex = query.indexOf("SELECT") + 6;
        int endIndex = query.indexOf("FROM");
        String columnsString = query.substring(startIndex, endIndex).trim();
        if ("*".equals(columnsString)) {
            // "*"�� ��� ��� ���� ����
            return tables.get(0).getRows().isEmpty() ?
                    Collections.emptyList() :
                    new ArrayList<>(tables.get(0).getRows().get(0).keySet());
        } else {
            return Arrays.asList(columnsString.split(","));
        }
    }


    private String extractGroupByColumn(String query) {
        // GROUP BY ������ �׷�ȭ�� ���� �����ϴ� ����
        // ���⼭�� ����ȭ�� ���� "GROUP BY" ���� �ڿ� ���� ���� �����Ѵٰ� ����
        int groupByIndex = query.indexOf("GROUP BY");
        return query.substring(groupByIndex + 8).trim();
    }

    private String extractOrderByColumn(String query) {
        // ORDER BY ������ ������ ���� �����ϴ� ����
        // ���⼭�� ����ȭ�� ���� "ORDER BY" ���� �ڿ� ���� ���� �����Ѵٰ� ����
        int orderByIndex = query.indexOf("ORDER BY");
        return query.substring(orderByIndex + 8).trim();
    }

    private Table executeDistinct(List<String> selectedColumns) {
        // DISTINCT ó�� ����
        // �ߺ��� �����Ͽ� ��� ��ȯ
        Table table = tables.get(0); // ���⼭�� ù ��° ���̺��� ����Ѵٰ� ����
        return table.distinct(selectedColumns);
    }

 // executeGroupBy �޼��� ����
    private Table executeGroupBy(List<String> selectedColumns, String groupByColumn) {
        // GROUP BY ó�� ����
        // �׷�ȭ�� ��� ��ȯ
        Table table = tables.get(0); // ���⼭�� ù ��° ���̺��� ����Ѵٰ� ����
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
        // ORDER BY ó�� ����
        // ���ĵ� ��� ��ȯ
        Table table = tables.get(0); // ���⼭�� ù ��° ���̺��� ����Ѵٰ� ����
        return table.orderBy(selectedColumns, orderByColumn);
    }

 // executeSelect �޼��� ����
    private Table executeSelect(List<String> selectedColumns) {
        // SELECT ó�� ����
        // ���õ� ���� ��ȯ
        Table table = tables.get(0); // ���⼭�� ù ��° ���̺��� ����Ѵٰ� ����
        List<Map<String, String>> selectedRows = table.getRows().stream()
                .map(row -> {
                    if (selectedColumns.isEmpty()) {
                        // ���õ� ���� ������ ��� �� ����
                        return new HashMap<>(row);
                    } else {
                        // ���õ� ���� ����
                        return selectedColumns.stream()
                                .filter(row::containsKey)
                                .collect(Collectors.toMap(column -> column, row::get));
                    }
                })
                .collect(Collectors.toList());

        return new Table(selectedRows);
    }


    // Table Ŭ������ ���� �޼����
    static class Table {
        private List<Map<String, String>> rows;

        public Table(List<Map<String, String>> rows) {
            this.rows = rows;
        }

        public Table distinct(List<String> selectedColumns) {
            // DISTINCT ó�� ����
            // �ߺ��� �����Ͽ� ��� ��ȯ
            List<Map<String, String>> distinctRows = rows.stream()
                    .distinct()
                    .collect(Collectors.toList());
            return new Table(distinctRows);
        }

        public Table groupBy(List<String> selectedColumns, String groupByColumn) {
            // GROUP BY ó�� ����
            // �׷�ȭ�� ��� ��ȯ
            // ���⼭�� �ܼ��� �׷�ȭ�� ����� �����ϴ� ������ ����
            return new Table(rows); // Placeholder logic
        }

        public Table orderBy(List<String> selectedColumns, String orderByColumn) {
            // ORDER BY ó�� ����
            // ���ĵ� ��� ��ȯ
            List<Map<String, String>> sortedRows = rows.stream()
                    .sorted((r1, r2) -> r1.get(orderByColumn).compareTo(r2.get(orderByColumn)))
                    .collect(Collectors.toList());
            return new Table(sortedRows);
        }

        public Table select(List<String> selectedColumns) {
            // SELECT ó�� ����
            // ���õ� ���� ��ȯ
            // ���⼭�� ��� ���� �����ϴ� ������ ����
            return new Table(rows);
        }

        public List<Map<String, String>> getRows() {
            return rows;
        }
    }
}
