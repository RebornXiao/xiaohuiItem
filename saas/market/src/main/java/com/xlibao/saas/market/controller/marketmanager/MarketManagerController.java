package com.xlibao.saas.market.controller.marketmanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.market.MarketService;
import com.xlibao.saas.market.service.market.ShelvesService;
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
    @Autowired
    private ShelvesService shelvesService;

    @ResponseBody
    @RequestMapping(value = "searchMarkets")
    public JSONObject searchMarkets() {
        return marketService.searchMarkets();
    }

    @ResponseBody
    @RequestMapping(value = "getMarket")
    public JSONObject getMarket() {
        return marketService.getMarket();
    }

    @ResponseBody
    @RequestMapping(value = "getAllMarkets")
    public JSONObject getAllMarkets() {
        return marketService.getAllMarkets();
    }

    //获取组(走道)、货架(单元)、层(楼层)的值
    @ResponseBody
    @RequestMapping(value = "getShelvesMarks")
    public JSONObject getShelvesMarks() {
        return shelvesService.getShelvesMarks();
    }

    //获取弹夹的值
    @ResponseBody
    @RequestMapping(value = "loaderClipDatas")
    public JSONObject loaderClipDatas() {
        return shelvesService.loaderClipDatas();
    }

    @ResponseBody
    @RequestMapping(value = "checkPrepareActionTask")
    public JSONObject checkPrepareActionTask() {
        return shelvesService.checkPrepareActionTask();
    }

    @ResponseBody
    @RequestMapping(value = "cancelPrepareActionTask")
    public JSONObject cancelPrepareActionTask() {
        return shelvesService.cancelPrepareActionTask();
    }

    @ResponseBody
    @RequestMapping(value = "prepareAction")
    public JSONObject prepareAction() {
        return shelvesService.prepareAction();
    }
}
