package com.xlibao.saas.market.service.market;

/**
 * @author chinahuangxc on 2017/8/29.
 */
public enum MarketRelationshipTypeEnum {

    FOCUS(1, "关注"),
    COURIER(2, "配送")
    ;

    private int key;
    private String value;

    MarketRelationshipTypeEnum(int key, String value) {
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