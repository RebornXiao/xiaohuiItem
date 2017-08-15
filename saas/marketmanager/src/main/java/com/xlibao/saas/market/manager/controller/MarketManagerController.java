package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market/manager/market")
public class MarketManagerController extends BaseController {

    @RequestMapping("/markets")
    public String markets(ModelMap map) {

        String province;
        String city;
        String district;
        String street;
        int street_number;

        int type;
        int status;
        int deliveryMode;

        map.put("markets", new JSONArray());

        return jumpPage(map, LogicConfig.FTL_MARKET_LIST, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
    }
}