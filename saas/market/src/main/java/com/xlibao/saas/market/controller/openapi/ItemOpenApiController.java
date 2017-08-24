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

    /**
     * <pre>
     *
     * </pre>
     */
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

    /**
     * <pre>
     *     <b>商品分组</b> -- 归类每个店的商品集合
     *
     *     <b>访问地址：</b>http://domainName/market/item/openapi/splitItems.do
     *     <b>访问方式：</b>GET/POST；推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>items</b> - String 分组商品ID，必填参数；格式为：itemId,itemId,itemId 如：100000,100001,100002。
     *
     *     <b>返回：</b>
     *          <b>datas</b> - JSONArray 每个元素都为一个JSONObject，结构：
     *              <b>marketId</b> - long 仓库ID
     *              <b>marketName</b> - String 仓库名字
     *              <b>items</b> - JSONArray 商品ID
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "splitItems")
    public JSONObject splitItems() {
        return itemService.splitItems();
    }

    /**
     * <pre>
     *     <b>下架商品</b>
     *
     *     <b>访问地址：</b>http://domainName/market/item/openapi/offShelves.do
     *     <b>访问方式：</b>GET/POST；推荐使用POST
     *
     *     <b>参数：</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "offShelves")
    public JSONObject offShelves() {
        return itemService.offShelves();
    }

    /**
     * <pre>
     *     <b>上架商品</b>
     *
     *     <b>访问地址：</b>http://domainName/market/item/openapi/onShelves.do
     *     <b>访问方式：</b>GET/POST；推荐使用POST
     *
     *     <b>参数：</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "onShelves")
    public JSONObject onShelves() {
        return itemService.onShelves();
    }

    /**
     * <pre>
     *     <b>是否存在商品</b>
     *
     *     <b>访问地址：</b>http://domainName/market/item/openapi/existItemTemplate.do
     *     <b>访问方式：</b>GET/POST；推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>marketId</b> - long 商店ID，必填参数。
     *          <b>itemTemplateId</b> - long 商品模版ID，必填参数。
     *
     *     <b>返回：</b>仅返回成功或失败的提示
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "existItemTemplate")
    public JSONObject existItemTemplate() {
        return itemService.existItemTemplate();
    }
}