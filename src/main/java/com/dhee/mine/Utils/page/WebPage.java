package com.dhee.mine.Utils.page;

import com.dhee.mine.Utils.enty.document.WebDocumentFetcher;
import lombok.Data;
import org.jsoup.nodes.Document;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.dhee.mine.Utils.enty.document.WebDocumentFetcher.fetchDocument;
import static com.dhee.mine.Utils.injection.SQLInjectionUtil.createMarkerPayload;
import static com.dhee.mine.Utils.injection.SQLInjectionUtil.getPageTagContentMap;

@Data
public abstract class WebPage {
    //请求页面的payload
    private String payload;
    //页面的文档信息
    private Document document;
    //页面标签-内容字典
    private Map<String, Set<String>> tagContentMap;
    //指定的标签内容的集合
    private Set<String> contentSet;

    private Set<String> contentSetFlushed;

    public WebPage(Map<String, String> paramMap, int possibleColumns, String flag, Boolean includeDashes) {
        this.payload = createMarkerPayload(possibleColumns, flag, includeDashes);
        this.document = WebDocumentFetcher.fetchDocument(paramMap, payload);
        this.tagContentMap = getPageTagContentMap(document);
    }

    public WebPage(Map<String, String> parameters, String pagePayload) {
        this.payload = pagePayload;
        this.document = WebDocumentFetcher.fetchDocument(parameters, pagePayload);
        this.tagContentMap = getPageTagContentMap(document);
    }

    public void setContentSet(String tag) {
        this.contentSet = new HashSet<>();
        for (String content : getTagContentMap().getOrDefault(tag, new HashSet<>())) {
            this.contentSet.add(content.replace(" ", ""));
        }
//        this.contentSet=getTagContentMap().getOrDefault(tag, new HashSet<>());
    }

}
