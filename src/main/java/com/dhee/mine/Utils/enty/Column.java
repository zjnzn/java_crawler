package com.dhee.mine.Utils.enty;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Column {
    private String column_name;
    private List<String> column_values;

    public Column(String column_name) {
        this.column_name = column_name;
    }
}
