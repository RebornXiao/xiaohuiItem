package com.xlibao.saas.market.controller.marketmanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.market.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhumg on 8/15.
 */
@Controller
@RequestMapping(value = "/marketmanager")
public class MarketManagerController {

    @Autowired
    private MarketService marketService;

    @ResponseBody
    @RequestMapping(value = "searchMarkets")
    public JSONObject searchMarkets() {
        return marketService.searchMarkets();
    }

    @ResponseBody
    @RequestMapping(value = "getMarket")
    public JSONObject getMarket() {
        return marketService.findMarket();
    }
}
