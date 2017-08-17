package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.marketmanager.MarketManagerService;
import com.xlibao.saas.market.manager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/marketmanager/market")
public class MarketManagerController extends BaseController {

    @Autowired
    private MarketManagerService marketManagerService;

    @RequestMapping("/markets")
    public String markets(ModelMap map) {

        String province = getUTF("province", "");
        String city = getUTF("city", "");
        String district = getUTF("district", "");
        String street = getUTF("street", "");
        long streetId = getLongParameter("streetId", -1);

        int type = getIntParameter("type", -1);
        int status = getIntParameter("status", -1);
        int deliveryMode = getIntParameter("deliveryMode", -1);

        MarketEntry entry = new MarketEntry();
        entry.setProvince(province);
        entry.setCity(city);
        entry.setDistrict(district);
        entry.setStreet(street);
        entry.setStreetId(streetId);
        entry.setType(type);
        entry.setStatus(status);
        entry.setDeliveryMode(deliveryMode);

        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        JSONObject marketJson = marketManagerService.searchMarkets(entry, pageSize, pageIndex);

        if (marketJson.getIntValue("code") != 0) {
            return remoteFail(map, marketJson, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
        }

        JSONObject response = marketJson.getJSONObject("response");

        map.put("markets", response.getJSONArray("data"));
        map.put("count", response.getIntValue("count"));

        //////////////////
        map.put("province", province);
        map.put("city", city);
        map.put("district", district);
        map.put("street", street);
        map.put("streetId", String.valueOf(streetId));
        map.put("type", type);
        map.put("status", status);
        map.put("deliveryMode", deliveryMode);

        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);

        return jumpPage(map, LogicConfig.FTL_MARKET_LIST, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
    }

    @RequestMapping("/merketEdit")
    public String merketEdit(ModelMap map) {
        long id = getLongParameter("id", 0);
        if (id != 0) {
            JSONObject itemJson = marketManagerService.getMarket(id);
            if (itemJson.getIntValue("code") != 0) {
                return remoteFail(map, itemJson, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TEMPLATE);
            }
            map.put("merket", itemJson.getJSONObject("response"));
        }
        return jumpPage(map, LogicConfig.FTL_MARKET_EDIT, LogicConfig.TAB_MARKET, LogicConfig.FTL_MARKET_LIST);
    }

}