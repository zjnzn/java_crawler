package com.dhee.mine.Utils.extractor;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FormParameterExtractor {

    public Queue<Map<String, String>> extractFormParameters(Elements formElements) {
        // 处理表单元素
        Queue<Map<String, String>> paramMapQueue = new LinkedList<>();
        for (Element formElement : formElements) {
            Elements inputElements = formElement.select("input");
            Map<String, String> formParameters = new HashMap<>();
            for (Element inputElement  : inputElements) {
                String name = inputElement .attr("name");
                String value = inputElement .attr("value");
                String type = inputElement .attr("type");
                if (!type.equals("submit")) {
                    formParameters.put(name, null);
                } else {
                    formParameters.put(name, value);
                }
            }
            paramMapQueue.add(formParameters);
        }
        return paramMapQueue;
    }
}
