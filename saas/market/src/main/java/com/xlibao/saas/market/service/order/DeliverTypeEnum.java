package com.xlibao.saas.market.service.order;

/**
 * @author chinahuangxc on 2017/8/2.
 */
public enum DeliverTypeEnum {

    PICKED_UP(1, "自提"),
    DISTRIBUTION(2, "配送"),
    ;

    private int key;
    private String value;

    DeliverTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}