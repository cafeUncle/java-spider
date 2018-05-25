package com.example.spiderjava.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.spiderjava.model.Article;
import com.example.spiderjava.util.Constant;
import com.example.spiderjava.util.OkHttpUtil;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BinanceSpider {
    // 币安的pageSize固定30条

    String binanceHost = "https://support.binance.com%s";

    String binanceUrl_newCoin = "https://support.binance.com/hc/zh-cn/sections/115000106672?page={}";
    String binanceUrl_news = "https://support.binance.com/hc/zh-cn/sections/115000202591?page={}";

    static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
    }

    public List<Article> spiderNewCoin(Integer page) {

        List<Article> articles = null;
        try {
            Response response = OkHttpUtil.connect(String.format(binanceUrl_newCoin, page), null, headers, OkHttpUtil.GET);
            String result = response.body().string();
            articles = new ArrayList<>();
            String regex = "<a href=\"(.+?)\" class=\"article-list-link\">(.+?)</a>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(result);
            while(matcher.find()) {
                Article article = new Article();
                article.setMarketId(Constant.MARKET_ID_BINANCE);
                article.setTitle(matcher.group(2));
                article.setContent(String.format(binanceHost, matcher.group(1)));
                articles.add(article);
            }
            System.out.println(articles.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    public List<Article> spiderNews(Integer page) {

        List<Article> articles = null;
        try {
            Response response = OkHttpUtil.connect(String.format(binanceUrl_news, page), null, headers, OkHttpUtil.GET);
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
        String result= "<a href=\"/hc/zh-cn/articles/360004168831-Binance%E5%B0%86%E4%BA%8E2018-05-24%E6%97%A5%E4%B8%8A%E5%B8%82Skycoin-SKY-\" class=\"article-list-link\">Binance将于2018/05/24日上市Skycoin（SKY）</a>";
        String regex = "<a href=\"(.+?)\" class=\"article-list-link\">(.+?)</a>";
//        String result = "123312";
//        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);
        while(matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }
}
