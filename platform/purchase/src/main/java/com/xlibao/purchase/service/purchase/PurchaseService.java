package com.xlibao.purchase.service.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */
public interface PurchaseService {
    /**
     * 供应商列表查询
     * @return
     */
    JSONObject searchSupplierPage();

    /**
     * 添加供应商
     * @return
     */
    JSONObject saveSupplier();

    /**
     * 供应商集合
     * @return
     */
    JSONObject getAllSupplier();

    /**
     * 修改供应商信息
     * @return
     */
    JSONObject updateSupplier();
    /**
     * 停用启用供应商
     * @return
     */
    JSONObject updateSupplierStatus();

    /**
     * 获取供应商详情
     * @return
     */
    JSONObject getSupplier();
}
