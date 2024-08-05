package com.dhee.mine.Utils.enty;

import lombok.Data;

@Data
public class UrlEntity {
    private int id;
    private String url;
    private int sql;
    private int xss;
    private int effect;
    private int user_id;
}
