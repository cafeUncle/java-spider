package com.example.spiderjava.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.spiderjava.model.Article;
import com.example.spiderjava.model.Notice;
import com.example.spiderjava.util.OkHttpUtil;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HuoBiSpider {

    String huoBiUrl = "https://www.huobipro.com/-/x/hb/p/api/contents/pro/list_notice?page=%d&limit=%d&language=zh-cn";
    String huoBiDetailUrl = "https://www.huobipro.com/-/x/hb/p/api/contents/pro/notice/%s";

    static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
    }

    public List<Article> spider(Integer page, Integer pageSize) {

        List<Article> articles = null;
        try {
            Response response = OkHttpUtil.connect(String.format(huoBiUrl, page, pageSize), null, headers, OkHttpUtil.GET);
            String result = response.body().string();
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray items = data.getJSONArray("items");
            articles = JSONArray.parseObject(items.toJSONString(), new TypeReference<ArrayList<Article>>() {});
            System.out.println(articles.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    public static void main(String[] args) {
        List<Article> spider = new HuoBiSpider().spider(1, 10);
        System.out.println(spider.get(0).getContent());
    }
}
