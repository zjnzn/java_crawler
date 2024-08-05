package com.dhee.mine.Utils.enty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
public class Database {
    private String database_name;
    private List<Table> tables = new ArrayList<>();

    public Database(String database_name) {
        this.database_name = database_name;
    }

    public void setTables(List<String> tableNameSet) {
        for (String tableName : tableNameSet) {
            Table table = new Table(tableName);
            tables.add(table);
        }
    }
}
