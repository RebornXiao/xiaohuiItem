package com.xlibao.saas.market.service.order;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.exception.XlibaoRuntimeException;

/**
 * @author chinahuangxc on 2017/8/11.
 */
public enum OrderErrorCodeEnum {

    ORDER_STATUS_ERROR(3000, "错误的订单状态"),
    ORDER_HAS_ACCEPT(3001, "订单已被接取"),
    ;

    private int key;
    private String value;

    OrderErrorCodeEnum(int key, String value) {
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

    public void throwException() {
        throwException(getValue());
    }

    public void throwException(String message) {
        throw new XlibaoRuntimeException(getKey(), message);
    }
}