package com.xlibao.saas.market.service.market;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;

/**
 * @author chinahuangxc on 2017/7/19.
 */
public enum MarketErrorCodeEnum {

    /** 1000 -- 您所在区域暂时未找到合适的商店 */
    CAN_NOT_FIND_MARKET(1000, "您所在区域暂时未找到合适的商店"),
    /** 1001 -- 错误的商店信息 */
    ERROR_MARKET_INFORMATION(1001, "错误的商店信息"),
    ;

    private int key;
    private String value;

    MarketErrorCodeEnum(int key, String value) {
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
