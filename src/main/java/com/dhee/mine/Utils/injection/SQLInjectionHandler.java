package com.dhee.mine.Utils.injection;

import com.dhee.mine.Utils.page.WebPageBased;
import com.dhee.mine.Utils.page.WebPageChanged;

import java.util.*;
import java.util.stream.Collectors;

import static com.dhee.mine.Utils.injection.SQLInjectionUtil.*;


public class SQLInjectionHandler {
    private final WebPageBased basePage;
    private final WebPageChanged changedPage;
    public int injectionColumnCount;
    Map<String, String> parameters;
    String flag;
    private WebPageChanged injectionChangedPage;

    public SQLInjectionHandler(Map<String, String> parameters, String flag) {
        // 初始化
        this.parameters = parameters;
        this.flag = flag;
        this.injectionColumnCount = getInjectionColumnNum(parameters, flag);

        basePage = new WebPageBased(parameters, injectionColumnCount, flag, true);
        changedPage = new WebPageChanged(parameters, injectionColumnCount, flag, false);

        List<String> differentTagsList = comparePages(basePage.getTagContentMap(), changedPage.getTagContentMap());
        for (String tag : differentTagsList) {
            basePage.setContextDifferentTag(tag);
            basePage.setContentSet(tag);
            changedPage.setContentSet(tag);
            handleBasePage();
        }
    }
    public List<String> processResult(String pay) {

        List<String> resultList = new ArrayList<>();
        injectionChangedPage = new WebPageChanged(parameters, pay);
        injectionChangedPage.setContentSet(basePage.getContextDifferentTag());
        handleInjectionPage();
        if (injectionChangedPage.getContentSetFlushed() != null) {
            for (String s : injectionChangedPage.getContentSetFlushed()) {
                String[] split;
                if (basePage.getResultSplit().isEmpty()) {
                    split = s.split(",", -1);

                } else {
                    split = s.split(basePage.getResultSplit());
                }
                if (split.length > 1) {
                    resultList.add(split[1]);
                }
//                for (String string : Arrays.stream(split)
//                        .collect(Collectors.toList())) {
//                    resultList.addAll(Arrays.stream(string.split(",",-1))
//                            .collect(Collectors.toList()));
//                }
//                for (String spl : referencePage.getResultSplit().split("\\|")) {
//                    if (!spl.isEmpty() && s.startsWith(spl)) {
//                        if (resultList.size() > 1){
//                            resultList.remove(0);
//                        }
//                    }
//                }
            }
        }

//        int count = resultList.size();
//        int halfSize = count - count / referencePage.getSqlInjectionColumnsPositions().size();
//        for (int i = 0; i < halfSize; i++) {
//            resultList.remove(resultList.size() - 1);
//        }
//        System.out.println(resultList.size());
        return resultList;
    }

    public void handleInjectionPage() {
        Set<String> uniqueToSet1 = new HashSet<>(basePage.getContentSet());
        uniqueToSet1.removeAll(injectionChangedPage.getContentSet());
        Set<String> uniqueToSet2 = new HashSet<>(injectionChangedPage.getContentSet());
        uniqueToSet2.removeAll(basePage.getContentSet());
        Set<String> preSet1 = new HashSet<>(extractStrings(uniqueToSet1, basePage.getPayload().replace(" ", "")));
        Set<String> preSet2 = new HashSet<>(extractStrings(uniqueToSet2, injectionChangedPage.getPayload().replace(" ", "")));
        preSet2.removeAll(preSet1);
        if (!preSet2.isEmpty()) {
            injectionChangedPage.setContentSetFlushed(preSet2);
        }
    }

    public void handleBasePage() {

        Set<String> uniqueToSet1 = new HashSet<>(basePage.getContentSet());
        uniqueToSet1.removeAll(changedPage.getContentSet());
        Set<String> uniqueToSet2 = new HashSet<>(changedPage.getContentSet());
        uniqueToSet2.removeAll(basePage.getContentSet());

        Set<String> preSet1 = new HashSet<>(extractStrings(uniqueToSet1, basePage.getPayload().replace(" ", "")));
        Set<String> preSet2 = new HashSet<>(extractStrings(uniqueToSet2, changedPage.getPayload().replace(" ", "")));

        Set<String> finalSet1 = new HashSet<>(preSet1);
        Set<String> finalSet2 = new HashSet<>(preSet2);
        finalSet1.removeAll(preSet2);
        finalSet2.removeAll(preSet1);
        if (!finalSet1.isEmpty()) {
            basePage.setContentSetFlushed(finalSet1);
            changedPage.setContentSetFlushed(finalSet2);
            for (String referencePageContentSet : basePage.getContentSetFlushed()) {
                String[] split = referencePageContentSet.split("---");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    stringBuilder.append(split[i]);
                    if (i < split.length - 1) {
                        stringBuilder.append("|");
                    }
                }

                basePage.setResultSplit(stringBuilder.toString());
                for (String changePageContentSet : changedPage.getContentSetFlushed()) {
                    String[] split1 = changePageContentSet.split(basePage.getResultSplit());
                    basePage.setSqlInjectionColumnsPositions(Arrays.stream(split1)
                            .filter(data -> !data.isEmpty())
                            .filter(data -> !data.equals(" "))
                            .collect(Collectors.toList()));
                }
            }
        }
    }
}
