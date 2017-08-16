package com.xlibao.saas.market.service.item;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.exception.XlibaoRuntimeException;

/**
 * @author chinahuangxc on 2017/7/20.
 */
public enum MarketItemErrorCodeEnum {

    /** 2000 -- 错误的商品分类 */
    ITEM_TYPE_ERROR(2000, "错误的商品分类"),
    /** 2001 -- 无效的商品数据 */
    INVALID_ITEMS(2001, "无效的商品数据"),
    /** 2002 -- 商品下架状态 */
    ITEM_STATUS_ERROR_OFF_LINE(2002, "商品下架状态"),
    /** 2003 -- 超出限购数量 */
    BUY_BEYOND_CONTROL(2003, "超出限购数量"),
    /** 2004 -- 商品库存不足 */
    ITEM_STOCK_NOT_ENOUGH(2004, "库存不足"),
    /** 2005 -- 商品存于不销售状态 */
    ITEM_UN_SELL(2005, "商品存于不销售状态"),
    /** 2006 -- 小于最低购买数量 */
    LESS_THAN_MINIMUM_SELL(2006, "小于最低购买数量"),
    /** 2007 -- 大于最多购买数量 */
    GREATER_THAN_MAXIMUM_SELL(2007, "大于最多购买数量"),
    /** 2008 -- 购买数量有误 */
    BUY_QUANTITY_ERROR(2008, "购买数量有误"),
    ;

    private int key;
    private String value;

    MarketItemErrorCodeEnum(int key, String value) {
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
