package com.dhee.mine.Utils.payload;

public class PayloadSchema extends PayloadBuild {
    public PayloadSchema() {
        super("schema_name", "schemata");
    }

    public String getPayload(String sign, int possibleColumns) {
        return (basePayloadBuild(sign, possibleColumns)
                .append(" #")
                .toString());
    }
}
