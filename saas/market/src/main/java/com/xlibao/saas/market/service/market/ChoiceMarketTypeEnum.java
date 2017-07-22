package com.xlibao.saas.market.service.market;

/**
 * @author chinahuangxc on 2017/7/19.
 */
public enum ChoiceMarketTypeEnum {

    PROVINCE(1, "省"),
    CITY(2, "市"),
    DISTRICT(3, "区"),
    STREET(4, "街道"),
    MARKET(5, "店铺"),
    ;

    private int key;
    private String value;

    ChoiceMarketTypeEnum(int key, String value) {
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