package com.xlibao.saas.market.service.item;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;

/**
 * @author chinahuangxc on 2017/7/20.
 */
public enum ItemErrorCodeEnum {

    /** 2000 -- 错误的商品分类 */
    ITEM_TYPE_ERROR(2000, "错误的商品分类"),;

    private int key;
    private String value;

    ItemErrorCodeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public JSONObject response(String errorMsg) {
        return BasicWebService.fail(getKey(), errorMsg);
    }

    public JSONObject response() {
        return response(getValue());
    }
}
