package com.xlibao.saas.market.manager.controller;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.ordermanager.OrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market/manager/order")
public class OrderManagerController extends BaseController {

    @Autowired
    private OrderManagerService orderManagerService;

    @RequestMapping("/orders")
    public String searchPageOrders(ModelMap map) {

        //店铺ID
        long marketId = getLongParameter("marketId", 0);
        int orderState = getIntParameter("orderState", 0);
        String startTime = getUTF("sTime", "");
        String endTime = getUTF("eTime", "");
        String searchType = getUTF("searchType", "");
        String searchKey = getUTF("searchKey", "");

        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        JSONObject orderJson = orderManagerService.searchPageOrders(marketId, orderState, startTime, endTime, searchType, searchKey, pageSize, pageStartIndex);

        if (orderJson.getIntValue("code") != 0) {
            return remoteFail(map, orderJson, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_UNIT);
        }

        JSONObject response = orderJson.getJSONObject("response");

        map.put("marketId", marketId);
        map.put("orderState", orderState);
        map.put("searchType", searchType);
        map.put("searchKey", searchKey);
        map.put("pageIndex", pageStartIndex);
        map.put("sTime",startTime);
        map.put("eTime",endTime);
        map.put("orders", response.getJSONArray("data"));
        map.put("count", response.getIntValue("count"));

        return jumpPage(map, LogicConfig.FTL_ORDER_LIST, LogicConfig.TAB_ORDER, LogicConfig.TAB_ORDER_LIST);
    }
}
