package com.dhee.mine.Utils.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class HyperlinkExtractor {
    public Set<String> extractHyperlinks(Document document) {
        Set<String> hyperlinks  = new HashSet<>();
        Elements linkElements = document.select("a[href]");
        for (Element element : linkElements){
            String absoluteHref = element.attr("abs:href");
            hyperlinks .add(absoluteHref);
        }
        return hyperlinks ;
    }
}
