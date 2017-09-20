package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.purchase.SupplierManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/09/20.
 *
 */
@Controller
@RequestMapping(value = "marketmanager/purchase")
public class PurchaseManagerController extends BaseController {

    @Autowired
    SupplierManagerService supplierManagerService;
        /**
         * 供应商列表
         * @param map
         * @return
         */
        @RequestMapping("/purchasePage")
        public String searchPurchasePage(ModelMap map) {
            JSONObject supplierJson =  supplierManagerService.searchSupplierPage();
            JSONObject response = supplierJson.getJSONObject("response");
            JSONArray suppliers = response.getJSONArray("data");
            map.put("count", response.getIntValue("count"));

            map.put("supplierName", getUTF("supplierName",null));
            map.put("supplierType", getIntParameter("supplierType",-1));
            map.put("status", getIntParameter("status",-1));
            int pageIndex = getIntParameter("pageIndex", 1);
            map.put("pageIndex", pageIndex);
            map.put("pageSize", getPageSize());
            map.put("suppliers", suppliers);
            return jumpPage(map, LogicConfig.FTL_ITEM_TYPES, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TYPE);
        }

    /**
     * 跳转供应商添加
     * @param map
     * @return
     */
    @RequestMapping("/supplierAdd")
    public String supplierAdd(ModelMap map) {
        return jumpPage(map, LogicConfig.FTL_ITEM_TYPES, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TYPE);
    }
    /**
     * 添加供应商
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveSupplier")
    public JSONObject saveSupplier(ModelMap map) {
        return supplierManagerService.saveSupplier();
    }
    /**
     * 跳转供应商编辑
     * @param map
     * @return
     */
    @RequestMapping("/supplierEdit")
    public String supplierEdit(ModelMap map) {
        JSONObject jsonObject= supplierManagerService.getSupplier();
        JSONObject supplier = jsonObject.getJSONObject("response");
        map.put("supplier", supplier);
        return jumpPage(map, LogicConfig.FTL_ITEM_TYPES, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TYPE);
    }
    /**
     * 更新供应商
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateSupplier")
    public JSONObject updateSupplier(ModelMap map) {
        return supplierManagerService.updateSupplier();
    }

    /**
     * 启用停用供应商
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateSupplierStatus")
    public JSONObject updateSupplierStatus(ModelMap map) {
        return supplierManagerService.updateSupplierStatus();
    }

    /**
     * 供应商详情
     * @param map
     * @return
     */
    @RequestMapping("/supplierDetail")
    public String getSupplier(ModelMap map) {
        JSONObject jsonObject= supplierManagerService.getSupplier();
        JSONObject supplier = jsonObject.getJSONObject("response");
        map.put("supplier", supplier);

        /*************采购信息BEGIN*************/
        /*************采购信息*****END********/
        return jumpPage(map, LogicConfig.FTL_ITEM_TYPES, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TYPE);
    }

}
