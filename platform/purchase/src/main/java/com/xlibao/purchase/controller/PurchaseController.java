package com.xlibao.purchase.controller;

import com.alibaba.fastjson.JSONObject;

import com.xlibao.purchase.service.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/8/29.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 供应商列表查询
     * @return
     */
    @ResponseBody
    @RequestMapping("searchSupplierPage")
    public JSONObject searchSupplierPage(){
        return purchaseService.searchSupplierPage();
    }

    /**
     * 供应商集合
     * @return
     */
    @ResponseBody
    @RequestMapping("getAllSupplier")
    public JSONObject getAllSupplier(){
        return purchaseService.getAllSupplier();
    }



    /**
     * 添加供应商
     * @return
     */
    @ResponseBody
    @RequestMapping("saveSupplier")
    public JSONObject saveSupplier(){
        return purchaseService.saveSupplier();
    }
    /**
     * 修改供应商
     * @return
     */
    @ResponseBody
    @RequestMapping("updateSupplier")
    public JSONObject updateSupplier(){
        return purchaseService.updateSupplier();
    }


    /**
     * 停用启动供应商
     * @return
     */
    @ResponseBody
    @RequestMapping("updateSupplierStatus")
    public JSONObject updateSupplierStatus(){
        return purchaseService.updateSupplierStatus();
    }

    /**
     * 获取供应商详情
     * @return
     */
    @ResponseBody
    @RequestMapping("getSupplier")
    public JSONObject getSupplier(){
        return purchaseService.getSupplier();
    }


}
