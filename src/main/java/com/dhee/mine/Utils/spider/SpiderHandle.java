package com.dhee.mine.Utils.spider;

import com.dhee.mine.Utils.enty.document.WebDocumentFetcher;
import com.dhee.mine.Utils.extractor.HyperlinkExtractor;
import com.dhee.mine.Utils.enty.UrlEntity;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SpiderHandle {
    public List<UrlEntity> getUrlList(String url) throws URISyntaxException, IOException {
        List<UrlEntity> urlEntityList = new ArrayList<>();

        Document document = WebDocumentFetcher.fetchDocumentFromURL(url);
        HyperlinkExtractor hyperlinkExtractor = new HyperlinkExtractor();
        Set<String> hrefs = hyperlinkExtractor.extractHyperlinks(document);
        System.out.println(hrefs);
        Iterator<String> iterator = hrefs.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            try {
                WebDocumentFetcher.fetchDocumentFromURL(s);

                UrlEntity urlEntity = new UrlEntity();
                urlEntity.setUrl(s);
                urlEntity.setSql(0);
                urlEntity.setXss(0);
                urlEntity.setEffect(1);
                urlEntity.setUser_id(1);
                urlEntityList.add(urlEntity);
            } catch (URISyntaxException | IOException e) {
                iterator.remove();
            }
        }
        return urlEntityList;
    }
}
