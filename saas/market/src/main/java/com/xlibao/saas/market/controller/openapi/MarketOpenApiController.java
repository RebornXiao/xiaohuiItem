package com.xlibao.saas.market.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.market.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Controller
@RequestMapping(value = "/market/openapi")
public class MarketOpenApiController {

    @Autowired
    private MarketService marketService;

    /***
     * <pre>
     *     <b>找到商店</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "findMarket")
    public JSONObject findMarket() {
        return marketService.findMarket();
    }

    /**
     * <pre>
     *     <b>展示可用的商店</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "showMarkets")
    public JSONObject showMarkets() {
        return marketService.showMarkets();
    }

    /**
     * <pre>
     *     <b>过滤商店(寻找商店)</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "filterMarket")
    public JSONObject filterMarket() {
        return marketService.filterMarket();
    }
}
