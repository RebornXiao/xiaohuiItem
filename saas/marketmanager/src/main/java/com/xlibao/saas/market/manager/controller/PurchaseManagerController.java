package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.purchasemanager.PurchaseManagerService;
import com.xlibao.saas.market.manager.service.purchasemanager.SupplierManagerService;
import com.xlibao.saas.market.manager.service.purchasemanager.WarehouseManagerService;
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

    @Autowired
    WarehouseManagerService warehouseManagerService;

    @Autowired
    PurchaseManagerService purchaseManagerService;
        /**
         * 供应商列表
         * @param map
         * @return
         */
        @RequestMapping("/supplierPage")
        public String searchSupplierPage(ModelMap map) {
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
            return jumpPage(map, LogicConfig.FTL_SUPPLIER_LIST, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_SUPPLIER_LIST);
        }

    /**
     * 跳转供应商添加
     * @param map
     * @return
     */
    @RequestMapping("/supplierAdd")
    public String supplierAdd(ModelMap map) {
        return jumpPage(map, LogicConfig.FTL_SUPPLIER_ADD, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_SUPPLIER_LIST);
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
        return jumpPage(map, LogicConfig.FTL_SUPPLIER_EDIT, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_SUPPLIER_LIST);
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
        JSONObject commoditysJson= purchaseManagerService.getPurchaseCommodityS();
        JSONObject commoditysResponse = commoditysJson.getJSONObject("response");
        JSONArray commoditys = commoditysResponse.getJSONArray("datas");
        map.put("commoditys", commoditys);
        /*************采购信息*****END********/
        return jumpPage(map, LogicConfig.FTL_SUPPLIER_DETAIL, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_SUPPLIER_LIST);
    }

    /**
     * 仓库列表
     * @param map
     * @return
     */
    @RequestMapping("/warehousePage")
    public String searchWarehousePage(ModelMap map) {
        JSONObject warehouseJson =  warehouseManagerService.searchWarehousePage();
        JSONObject response = warehouseJson.getJSONObject("response");
        JSONArray warehouses = response.getJSONArray("data");
        map.put("count", response.getIntValue("count"));

        JSONObject warehousesJson =   warehouseManagerService.getAllWarehouse();
        JSONObject warehouseResp = warehousesJson.getJSONObject("response");
        JSONArray warehouseItem = warehouseResp.getJSONArray("datas");
        /**下拉列表**/
        map.put("warehouseItem", warehouseItem);

        map.put("warehouseName", getUTF("warehouseName",null));
        map.put("status", getIntParameter("status",-1));
        int pageIndex = getIntParameter("pageIndex", 1);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", getPageSize());
        /*****分页数据**/
        map.put("warehouses", warehouses);
        return jumpPage(map, LogicConfig.FTL_HOUSE_LIST, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_HOUSE_LIST);
    }

    /**
     * 跳转仓库添加
     * @param map
     * @return
     */
    @RequestMapping("/warehouseAdd")
    public String warehouseAdd(ModelMap map) {
        return jumpPage(map, LogicConfig.FTL_HOUSE_ADD, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_HOUSE_LIST);
    }
    /**
     * 添加仓库
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveWarehouse")
    public JSONObject saveWarehouse(ModelMap map) {
        return warehouseManagerService.saveWarehouse();
    }
    /**
     * 跳转仓库编辑
     * @param map
     * @return
     */
    @RequestMapping("/warehouseEdit")
    public String warehouseEdit(ModelMap map) {
        JSONObject jsonObject= warehouseManagerService.getWarehouse();
        JSONObject warehouse = jsonObject.getJSONObject("response");
        map.put("warehouse", warehouse);
        return jumpPage(map, LogicConfig.FTL_HOUSE_EDIT, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_HOUSE_LIST);
    }
    /**
     * 更新仓库
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateWarehouse")
    public JSONObject updateWarehouse(ModelMap map) {
        return warehouseManagerService.updateWarehouse();
    }

    /**
     * 仓库详情
     * @param map
     * @return
     */
    @RequestMapping("/warehouseDetail")
    public String warehouseDetail(ModelMap map) {
        JSONObject jsonObject= warehouseManagerService.getWarehouse();
        JSONObject warehouse = jsonObject.getJSONObject("response");
        map.put("warehouse", warehouse);

        /*************仓管员BEGIN*************/
        JSONObject userJson= warehouseManagerService.getWarehouseUsers();
        JSONObject userResp = jsonObject.getJSONObject("response");
        JSONArray users = userResp.getJSONArray("datas");
        map.put("users", users);
        /*************仓管员END********/
        return jumpPage(map, LogicConfig.FTL_HOUSE_DETAIL, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_HOUSE_LIST);
    }

    /**
     * 启用停用仓库
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateWarehouseStatus")
    public JSONObject updateWarehouseStatus(ModelMap map) {
        return warehouseManagerService.updateWarehouseStatus();
    }


    /**
     * 跳转仓管员添加
     * @param map
     * @return
     */
    @RequestMapping("/warehouseUserAdd")
    public String warehouseUseradd(ModelMap map) {
        return jumpPage(map, LogicConfig.FTL_HOUSE_ADDUSER, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_HOUSE_LIST);
    }
    /**
     * 添加仓管员
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveWarehouseUser")
    public JSONObject saveWarehouseUser(ModelMap map) {
        return warehouseManagerService.saveWarehouseUser();
    }

    /**
     * 删除仓管员
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteWarehouseUser")
    public JSONObject deleteWarehouseUser(ModelMap map) {
        return warehouseManagerService.deleteWarehouseUser();
    }


    /**
     * 初始化仓管员密码
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetWarehouseUserPwd")
    public JSONObject resetWarehouseUserPwd(ModelMap map) {
        return warehouseManagerService.updateWarehouseUserPassword();
    }

    /**
     * 采购单列表
     * @return
     */
    @RequestMapping("/purchasePage")
    public String searchPurchasePage(ModelMap map) {
        JSONObject purchaseJson =  purchaseManagerService.searchPurchasePage();
        JSONObject response = purchaseJson.getJSONObject("response");
        JSONArray purchases = response.getJSONArray("data");
        map.put("count", response.getIntValue("count"));

        JSONObject warehousesJson =   warehouseManagerService.getAllWarehouse();
        JSONObject warehouseResp = warehousesJson.getJSONObject("response");
        JSONArray warehouseItem = warehouseResp.getJSONArray("datas");
        /**下拉列表**/
        map.put("warehouseItem", warehouseItem);

        map.put("supplierName", getUTF("supplierName",null));
        map.put("warehouseID", getIntParameter("warehouseID",-1));
        map.put("status", getIntParameter("status",-1));
        int pageIndex = getIntParameter("pageIndex", 1);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", getPageSize());
        /*****分页数据**/
        map.put("purchases", purchases);
        return jumpPage(map, LogicConfig.FTL_PURCHASE_LIST, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_PURCHASE_LIST);
    }

    /**
     * 采购单详情
     * @param map
     * @return
     */
    @RequestMapping("purchaseDetail")
    public String purchaseDetail(ModelMap map) {
        JSONObject purchaseJson= purchaseManagerService.getPurchase();
        JSONObject purchase = purchaseJson.getJSONObject("response");
        map.put("purchase", purchase);

        /*************采购物品BEGIN*************/
        JSONObject commoditysJson= purchaseManagerService.getPurchaseCommodityS();
        JSONObject commoditysResponse = commoditysJson.getJSONObject("response");
        JSONArray commoditys = commoditysResponse.getJSONArray("datas");
        map.put("commoditys", commoditys);
        /*************采购物品END********/
        return jumpPage(map, LogicConfig.FTL_PURCHASE_DETAIL, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_PURCHASE_LIST);
    }

    /**
     * 删除采购单
     * @return
     */
    @ResponseBody
    @RequestMapping("/deletePurchase")
    public JSONObject deletePurchase(ModelMap map) {
        return purchaseManagerService.deletePurchase();
    }

    /**
     * 跳转采购单添加
     * @param map
     * @return
     */
    @RequestMapping("/purchaseAdd")
    public String purchaseAdd(ModelMap map) {
        return jumpPage(map, LogicConfig.FTL_PURCHASE_ADD, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_PURCHASE_LIST);
    }


    /**
     * 跳转采购单编辑
     * @param map
     * @return
     */
    @RequestMapping("/purchaseEdit")
    public String purchaseEdit(ModelMap map) {
        JSONObject purchaseJson= purchaseManagerService.getPurchase();
        JSONObject purchase = purchaseJson.getJSONObject("response");
        map.put("purchase", purchase);

        /*************采购物品BEGIN*************/
        JSONObject commoditysJson= purchaseManagerService.getPurchaseCommodityS();
        JSONObject commoditysResponse = commoditysJson.getJSONObject("response");
        JSONArray commoditys = commoditysResponse.getJSONArray("datas");
        map.put("commoditys", commoditys);
        /*************采购物品END********/
        return jumpPage(map, LogicConfig.FTL_PURCHASE_EDIT, LogicConfig.TAB_PURCHASE, LogicConfig.TAB_PURCHASE_LIST);
    }
    /**
     * 添加采购单同时需要添加采购物品
     * @return
     */
    @ResponseBody
    @RequestMapping("/savePurchase")
    public JSONObject savePurchase(ModelMap map) {
        return purchaseManagerService.savePurchase();
    }

    /**
     * 更新采购单。删除采购物品再添加采购物品
     * @return
     */
    @ResponseBody
    @RequestMapping("/updatePurchase")
    public JSONObject updatePurchase(ModelMap map) {
        return purchaseManagerService.updatePurchase();
    }
}
