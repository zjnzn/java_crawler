package com.dhee.mine.Utils.page;

import lombok.ToString;

import java.util.Map;


@ToString
public class WebPageChanged extends WebPage {

    public WebPageChanged(Map<String, String> paramMap, int possibleColumns, String flag, Boolean withDashes) {
        super(paramMap, possibleColumns, flag, withDashes);
    }

    public WebPageChanged(Map<String, String> paramMap, String injectionPagePayload) {
        super(paramMap, injectionPagePayload);
    }
}
