package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.itemmanager.ItemManagerService;
import com.xlibao.saas.market.manager.service.purchasemanager.PurchaseManagerService;
import com.xlibao.saas.market.manager.service.purchasemanager.SupplierManagerService;
import com.xlibao.saas.market.manager.service.purchasemanager.WarehouseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by admin on 2017/10/16.
 *
 */
@Controller
@RequestMapping(value = "marketmanager/coupon")
public class CouponManagerController extends BaseController {

    @Autowired
    SupplierManagerService supplierManagerService;


        /**
         * 优惠券列表
         * @param map
         * @return
         */
        @RequestMapping("/couponPage")
        public String searchCouponPage(ModelMap map) {
            JSONObject couponJson =  supplierManagerService.searchSupplierPage();
            JSONObject response = couponJson.getJSONObject("response");
            JSONArray coupons = response.getJSONArray("data");
            map.put("count", response.getIntValue("count"));

            map.put("activeName", getUTF("activeName",""));
            map.put("activeType", getIntParameter("activeType",-1));
            map.put("activeStatus", getIntParameter("activeStatus",-1));
            int pageIndex = getIntParameter("pageIndex", 1);
            map.put("pageIndex", pageIndex);
            map.put("pageSize", getPageSize());
            map.put("coupons", coupons);
            return jumpPage(map, LogicConfig.FTL_COUPON_LIST, LogicConfig.TAB_COUPON, LogicConfig.TAB_COUPON_LIST);
        }

    /**
     * 跳转优惠券添加
     * @param map
     * @return
     */
    @RequestMapping("/couponAdd")
    public String supplierAdd(ModelMap map) {
        return jumpPage(map, LogicConfig.FTL_COUPON_ADD, LogicConfig.TAB_COUPON, LogicConfig.TAB_COUPON_LIST);
    }

    /**
     * 跳转优惠券编辑
     * @param map
     * @return
     */
    @RequestMapping("/couponEdit")
    public String supplierEdit(ModelMap map) {
        JSONObject jsonObject= supplierManagerService.getSupplier();
        JSONObject supplier = jsonObject.getJSONObject("response");
        map.put("supplier", supplier);
        return jumpPage(map, LogicConfig.FTL_COUPON_EDIT, LogicConfig.TAB_COUPON, LogicConfig.TAB_COUPON_LIST);
    }


    /**
     * 优惠券详情
     * @param map
     * @return
     */
    @RequestMapping("/couponDetail")
    public String getSupplier(ModelMap map) {
        return jumpPage(map, LogicConfig.FTL_COUPON_DETAIL, LogicConfig.TAB_COUPON, LogicConfig.TAB_COUPON_LIST);
    }
}
