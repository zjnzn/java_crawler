package com.dhee.mine.Utils.enty;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
public class Table {
    private String table_name;
    private List<Column> columns = new ArrayList<>();

    public Table(String table_name) {
        this.table_name = table_name;
    }

    public void setColumns(List<String> columnNameSet) {
        for (String columnName : columnNameSet) {
            columns.add(new Column(columnName));
        }
    }
}
