package com.xlibao.saas.market.controller.marketmanager;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.manager.MarketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@Controller
@RequestMapping(value = "/market/item/manager")
public class ItemManagerController {

    @Autowired
    private MarketItemService marketItemService;

    @ResponseBody
    @RequestMapping(value = "searchMarketItems")
    public JSONObject searchMarketItems() {
        return marketItemService.searchMarketItems();
    }
}
