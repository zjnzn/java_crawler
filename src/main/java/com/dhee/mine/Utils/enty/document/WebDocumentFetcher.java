package com.dhee.mine.Utils.enty.document;

import lombok.Setter;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebDocumentFetcher {
    @Setter
    static String targetUrl;
    @Setter
    static String requestCookie;

    static CloseableHttpClient httpClient = HttpClients.custom()
            .build();

    static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(2000)
            .build();



    public static Document fetchDocument() throws URISyntaxException, IOException {
        return fetchDocumentFromURL(targetUrl);
    }

    public static Document fetchDocument(Map<String, String> parameters, String payload) {
        List<NameValuePair> parameterList = processParameters(parameters, payload);
        try {
            return fetchDocumentFromURL(targetUrl, parameterList);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document fetchDocumentFromURL(String url, List<NameValuePair> params) throws URISyntaxException, IOException {
            URI uri = new URIBuilder(url)
                    .setParameters(params)
                    .setCharset(StandardCharsets.UTF_8)
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("cookie", requestCookie);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0); Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
            httpGet.setConfig(requestConfig);

            String entityString;
            entityString = EntityUtils.toString(httpClient.execute(httpGet).getEntity());

            return Jsoup.parse(entityString,String.valueOf(uri));
    }

    public static Document fetchDocumentFromURL(String url) throws URISyntaxException, IOException {
            URI uri = new URIBuilder(url)
                    .setCharset(StandardCharsets.UTF_8)
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("cookie", requestCookie);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0); Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
            httpGet.setConfig(requestConfig);

            String entityString;
            entityString = EntityUtils.toString(httpClient.execute(httpGet).getEntity());

        return Jsoup.parse(entityString, String.valueOf(uri));
    }

    public static List<NameValuePair> processParameters(Map<String, String> parameters, String value) {
        List<NameValuePair> parameterList = new ArrayList<>();
        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String name = entry.getKey();
                String val = entry.getValue();
                if (val == null) {
                    val = value;
                }
                NameValuePair parameter = new BasicNameValuePair(name, val);
                parameterList.add(parameter);
            }
        }
        return parameterList;
    }
}