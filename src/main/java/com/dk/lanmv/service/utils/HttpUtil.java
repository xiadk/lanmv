package com.dk.lanmv.service.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 这是描述
 * @author: DK
 * @create: 2019-11-28 10:03
 **/
@Slf4j
@Component
public class HttpUtil {

    public  String doGet(String url){
      return doGet(url, null);
    }

    public  String doGet(String url, Map<String, String> headers){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
//        httpGet.addHeader("user-agent", "__cfduid=d17eb0d6a612d8fea6954c4bb2ea3a4801574058813; UM_distinctid=16e7d375e43da5-01d479b99c999-3a614f0b-1fa400-16e7d375e44ca2; PHPSESSID=5qe4t1t6gj9akiel8kf19j1cr2; HISTORY={video:[{\"name\":\"\\u65E5\\u672CNoir-\\u5211\\u4E8BY\\u7684\\u53DB\\u4E71-\",\"link\":\"https://www.nfmovies.com/detail/?40166.html\",\"pic\":\"/static/uploads/allimg/191015/1c1ea843b8abfd8f.jpg\"},{\"name\":\"\\u653E\\u8BFE\\u540E\\u684C\\u6E38\\u4FF1\\u4E50\\u90E8\",\"link\":\"https://www.nfmovies.com/detail/?39681.html\",\"pic\":\"/static/uploads/allimg/191004/07ddab33a2596b1a.jpg\"},{\"name\":\"\\u53CC\\u5B50\\u6740\\u624B2019\",\"link\":\"https://www.nfmovies.com/detail/?40412.html\",\"pic\":\"/static/uploads/allimg/191020/2cf3df79751560de.jpg\"},{\"name\":\"\\u6D6E\\u73B0\",\"link\":\"https://www.nfmovies.com/detail/?39389.html\",\"pic\":\"/static/uploads/allimg/190926/9182ede884d086ab.jpg\"}]}; CNZZDATA1277238851=648928761-1574057600-https%253A%252F%252Fji-e.pw%252F%7C1574918826");
//        httpGet.addHeader("cookie", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
//        httpGet.addHeader("referer", "https://www.nfmovies.com/js/player/mp4.html?19112529");
//        httpGet.addHeader("accept", "gzip, deflate, br");
//        httpGet.addHeader("accept-language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
//        httpGet.addHeader("x-requested-with", "XMLHttpRequest");
//        httpGet.addHeader("accept-encoding", "https://www.nfmovies.com/js/player/mp4.html?19112529");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return getResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("链接异常"+e);
        }

        return null;
    }

    private static String getResponse(CloseableHttpResponse response) throws ParseException, IOException {
        String result = null;
        try {
            log.info("response={}",response);
            if(response.getStatusLine().getStatusCode()==200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }else {
                log.info("请求返回非200，response={},statusCode={}",response,response.getStatusLine().getStatusCode());
            }
        } finally {
            if(response != null) {
                response.getEntity().getContent().close();
            }
        }
        return result;
    }
}
