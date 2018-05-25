package com.example.spiderjava.controller;

import com.example.spiderjava.dao.ArticleMapper;
import com.example.spiderjava.dao.MarketMapper;
import com.example.spiderjava.dao.NoticeDetailMapper;
import com.example.spiderjava.model.*;
import com.example.spiderjava.spider.BinanceSpider;
import com.example.spiderjava.spider.HuoBiSpider;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class HelloController {

    @Autowired
    MarketMapper marketMapper;

    @Autowired
    HuoBiSpider huoBiSpider;

    @Autowired
    BinanceSpider binanceSpider;

    @Autowired
    NoticeDetailMapper noticeDetailMapper;

    @Autowired
    ArticleMapper articleMapper;

    @GetMapping("/api")
    public List<Market> all() {
        MarketExample marketExample = new MarketExample();
        marketExample.createCriteria();
        List<Market> markets = marketMapper.selectByExample(marketExample);
        return markets;
    }

    /**
     * 实时爬列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/huoBi/spider")
    public List<Article> huoBi(@RequestParam @Min(1) Integer page, @RequestParam @Min(1) Integer pageSize) {
        List<Article> articles = huoBiSpider.spider(page, pageSize);
        return articles;
    }

    /**
     * 从数据库
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/huoBi/article")
    public List<Article> huoBiNotice(@RequestParam @Min(1) Integer page, @RequestParam @Min(1) Integer pageSize) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria();
        return articleMapper.selectByExampleWithRowbounds(articleExample, new RowBounds((page-1) * pageSize, pageSize));
    }

    /**
     * 实时爬币安新币上线
     * @param page
     * @return
     */
    @GetMapping("/binance/spider/newCoin")
    public List<Article> binanceNewCoin(@RequestParam @Min(1) Integer page) {
        List<Article> articles = binanceSpider.spiderNewCoin(page);
        return articles;
    }

    /**
     * 实时爬币安最新公告
     * @param page
     * @return
     */
    @GetMapping("/binance/spider/news")
    public List<Article> binanceNews(@RequestParam @Min(1) Integer page) {
        List<Article> articles = binanceSpider.spiderNews(page);
        return articles;
    }




}
