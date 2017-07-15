package com.xlibao.saas.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.market.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @ResponseBody
    @RequestMapping(value = "lastMarket")
    public JSONObject lastMarket() {
        return marketService.lastMarket();
    }

    @ResponseBody
    @RequestMapping(value = "choiceMarket")
    public JSONObject choiceMarket() {
        return marketService.choiceMarket();
    }
}