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
    @RequestMapping(value = "findMarket")
    public JSONObject findMarket() {
        return marketService.findMarket();
    }

    @ResponseBody
    @RequestMapping(value = "filterMarket")
    public JSONObject filterMarket() {
        return marketService.filterMarket();
    }
}