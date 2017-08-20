package com.xlibao.saas.market.service.item;

/**
 * @author chinahuangxc on 2017/8/20.
 */
public enum PrepareActionStatusEnum {

    INVALID(0, "失效"),
    UN_EXECUTOR(1, "未执行"),
    COMPLETE(2, "完成"),
    ;

    private int key;
    private String value;

    PrepareActionStatusEnum(int key, String value) {
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