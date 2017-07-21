package com.xlibao.saas.market.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Controller
@RequestMapping(value = "/market/item/openapi")
public class ItemOpenApiController {

    @Autowired
    private ItemService itemService;

    @ResponseBody
    @RequestMapping(value = "homepage")
    public JSONObject homepage() {
        return itemService.homepage();
    }

    @ResponseBody
    @RequestMapping(value = "itemTypes")
    public JSONObject itemTypes() {
        return itemService.itemTypes();
    }

    @ResponseBody
    @RequestMapping(value = "findSubItemTypes")
    public JSONObject findSubItemTypes() {
        return itemService.findSubItemTypes();
    }

    @ResponseBody
    @RequestMapping(value = "findRecommendItems")
    public JSONObject findRecommendItems() {
        return itemService.findRecommendItems();
    }

    @ResponseBody
    @RequestMapping(value = "pageItems")
    public JSONObject pageItems() {
        return itemService.pageItems();
    }
}