package com.dhee.mine.Utils.payload;

public class PayloadTable extends PayloadBuild {
    public PayloadTable() {
        super("table_name", "tables");
    }

    public String getPayload(String sign, int possibleColumns, String schemaName) {
        return (basePayloadBuild(sign, possibleColumns)
                .append(" where table_schema ='")
                .append(schemaName)
                .append("' #")
                .toString());
    }
}
