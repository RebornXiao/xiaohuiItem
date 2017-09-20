package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

    /**
     * Created by admin on 2017/09/20.
     *
     */
    @Controller
    @RequestMapping(value = "marketmanager/purchase")
    public class PurchaseManagerController extends BaseController {

        /**
         * 采购单列表
         * @param map
         * @return
         */
        @RequestMapping("/purchaseList")
        public String searchPurchasePage(ModelMap map) {

            return jumpPage(map, LogicConfig.FTL_ITEM_TYPES, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TYPE);
        }

}
