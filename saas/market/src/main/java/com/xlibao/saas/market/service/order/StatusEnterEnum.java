package com.xlibao.saas.market.service.order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/8/2.
 */
public enum StatusEnterEnum {

    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 用户 使用 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    /** 11 -- 全部 */
    ALL(11, "全部"),
    /** 12 -- 待处理 */
    WAIT_PAYMENT(12, "待付款"),
    /** 13 -- 分批送 */
    WAIT_RECEIPT(13, "待收货"),
    /** 14 -- 配送中 */
    REFUND(14, "退款"),
    // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 用户 使用 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    ;

    private int key;
    private String value;

    StatusEnterEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private static final Map<Integer, StatusEnterEnum> statusEnters = new HashMap<>();

    static {
        StatusEnterEnum[] statusEnterEnumses = StatusEnterEnum.values();
        for (StatusEnterEnum statusEnterEnum : statusEnterEnumses) {
            statusEnters.put(statusEnterEnum.getKey(), statusEnterEnum);
        }
    }

    public static StatusEnterEnum getStatusEnterEnum(int key) {
        return statusEnters.get(key);
    }
}