package com.xlibao.saas.market.service;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;

/**
 * @author chinahuangxc on 2017/7/21.
 */
public enum ErrorCodeEnum {

    /** 100 -- 没有更多数据 */
    NO_MORE_DATA(100, "没有更多数据"),;

    private int key;
    private String value;

    ErrorCodeEnum(int key, String value) {
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
