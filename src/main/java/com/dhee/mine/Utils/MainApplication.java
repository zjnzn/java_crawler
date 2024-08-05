package com.dhee.mine.Utils;


import com.dhee.mine.Utils.enty.document.WebDocumentFetcher;
import com.dhee.mine.Utils.extractor.FormParameterExtractor;
import com.dhee.mine.Utils.enty.Database;
import com.dhee.mine.Utils.injection.SQLInjectionResult;
import com.dhee.mine.Utils.injection.SQLInjectionTest;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.dhee.mine.Utils.enty.document.WebDocumentFetcher.fetchDocument;


public class MainApplication {
    public List<Database> run() {

        String[] URLs = {
                "http://127.0.0.1:8080/sql_injection",
                "http://192.168.26.128:8083/vul/sqli/sqli_search.php",
                "http://192.168.26.128:8083/vul/sqli/sqli_x.php",
                "http://192.168.26.128:8082/Less-1/",
                "http://192.168.26.128:8081/vulnerabilities/sqli/",
                "http://192.168.26.128:8083/vul/sqli/sqli_id.php",
                "http://192.168.26.128:8083/vul/sqli/sqli_blind_b.php",
        };
        String cookie = "PHPSESSID=e0oji2ift57211vbcpgovnambg; security=low";
        int currentIndex = 4;
        WebDocumentFetcher.setTargetUrl(URLs[currentIndex]);
        WebDocumentFetcher.setRequestCookie(cookie);


        FormParameterExtractor fromExtractor = new FormParameterExtractor();
        Document document;
        try {
            document = WebDocumentFetcher.fetchDocument();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }


        Elements formElements = document.select("form");
        Queue<Map<String, String>> paramMapQueue = fromExtractor.extractFormParameters(formElements);
        Map<String, String> paramMap = new HashMap<>();
        if (currentIndex == 3) {
            paramMap.put("id", null);
        } else {
            paramMap = paramMapQueue.poll();
        }

        String sign = new SQLInjectionTest().getInjectionType(paramMap);
        return new SQLInjectionResult().databaseBlasting(paramMap, sign);
    }
}
