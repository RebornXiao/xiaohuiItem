package com.xlibao.common.exception.code;

import com.xlibao.common.exception.XlibaoRuntimeException;

/**
 * @author chinahuangxc on 2017/7/31.
 */
public enum OrderErrorCodeEnum {

    /** 20000 -- 找不到订单 */
    NOT_FOUND_ORDER(20000, "找不到订单"),
    /** 20001 -- 订单已支付 */
    HAS_PAYMENT_ORDER(20001, "订单已支付"),
    /** 20002 -- 订单已完成 */
    COMPLETE_ORDER(20002, "订单已完成"),
    /** 20003 -- 支付订单失败 */
    FAIL_PAYMENT_ORDER(20003, "支付订单失败"),
    /** 20004 -- 订单已取消 */
    CANCELED_ORDER(20004, "订单已被取消"),
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

    public void throwException() {
        throwException(getValue());
    }

    public void throwException(String message) {
        throw new XlibaoRuntimeException(getKey(), message);
    }
}