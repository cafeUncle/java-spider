package com.example.spiderjava.controller;

import com.example.spiderjava.dao.MarketMapper;
import com.example.spiderjava.dao.NoticeDetailMapper;
import com.example.spiderjava.model.*;
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
    NoticeDetailMapper noticeDetailMapper;

    @GetMapping("/api")
    public List<Market> all() {
        MarketExample marketExample = new MarketExample();
        marketExample.createCriteria();
        List<Market> markets = marketMapper.selectByExample(marketExample);
        return markets;
    }

    @GetMapping("/huoBi")
    public List<Article> huoBi(@RequestParam @Min(1) Integer page, @RequestParam @Min(1) Integer pageSize) {
        List<Article> spider = huoBiSpider.spider(page, pageSize);
        return spider;
    }

    @GetMapping("/huoBi/notice")
    public List<NoticeDetail> huoBiNotice(@RequestParam @Min(1) Integer page, @RequestParam @Min(1) Integer pageSize) {
        NoticeDetailExample noticeDetailExample = new NoticeDetailExample();
        noticeDetailExample.createCriteria();
        return noticeDetailMapper.selectByExampleWithRowbounds(noticeDetailExample, new RowBounds((page-1) * pageSize, pageSize));
    }


}
