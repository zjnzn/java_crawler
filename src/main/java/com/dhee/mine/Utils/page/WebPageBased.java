package com.dhee.mine.Utils.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
public class WebPageBased extends WebPage {
    private String contextDifferentTag;
    private List<String> sqlInjectionColumnsPositions;
    private String resultSplit;

    public WebPageBased(Map<String, String> paramMap, int possibleColumns, String flag, Boolean withDashes) {
        super(paramMap, possibleColumns, flag, withDashes);
    }

}
