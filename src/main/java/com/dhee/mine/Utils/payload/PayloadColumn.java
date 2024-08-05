package com.dhee.mine.Utils.payload;

public class PayloadColumn extends PayloadBuild {

    public PayloadColumn() {
        super("column_name", "columns");
    }

    public String getPayload(String sign, int possibleColumns, String schemaName, String tableName) {
        return (basePayloadBuild(sign, possibleColumns)
                .append(" where table_schema='")
                .append(schemaName)
                .append("' and table_name ='")
                .append(tableName)
                .append("' #")
                .toString());
    }
}
