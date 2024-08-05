package com.dhee.mine.Utils.injection;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dhee.mine.Utils.enty.document.WebDocumentFetcher.fetchDocument;

public class SQLInjectionTest {
    public String getInjectionType(Map<String, String> paramMap) {
        String[] payloads = {
                "1",
                "1'",
                "1' #",
                "1\"",
                "1\" #",
                "1')",
                "1') #",
                "1\")",
                "1\") #",
        };
        List<String> flags = new ArrayList<>();
        for (String payload : payloads) {
            Document injectedDocument = fetchDocument(paramMap, payload);
            if (injectedDocument.text().contains("You have an error in your SQL syntax")) {
                flags.add(payload);
            }
        }
        if (!flags.isEmpty()) {
            System.out.println("注入类别: 显式注入");
            System.out.println("注入标识: " + flags);
            System.out.print("注入类型: ");
            if (flags.size() > 8) {
                System.out.println("数值注入");
                return "1";
            } else if (flags.contains("1'") && flags.contains("1')") && flags.contains("1') #")) {
                System.out.println("单引号注入");
                return "1'";
            } else if (flags.contains("1\"") && flags.contains("1\")") && flags.contains("1\") #")) {
                System.out.println("双引号注入");
                return "1'";
            } else if (flags.contains("1'") && flags.contains("1' #") && flags.contains("1')")) {
                System.out.println("单引号和括号注入");
                return "1')";
            } else if (flags.contains("1\"") && flags.contains("1\" #") && flags.contains("1\")")) {
                System.out.println("双引号和括号注入");
                return "1\")";
            }
        } else {
            System.out.println("未检测到潜在注入.");
        }
        return null;
    }
}
