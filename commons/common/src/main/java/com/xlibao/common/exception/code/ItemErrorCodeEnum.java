package com.xlibao.common.exception.code;

import com.xlibao.common.exception.XlibaoRuntimeException;

/**
 * <pre>
 *     <b>商品错误码枚举类</b>
 *     <b>注意：</b>设定范围为 -- 40000至49999
 * </pre>
 *
 * @author chinahuangxc on 2017/8/17.
 */
public enum ItemErrorCodeEnum {

    /** 40000 -- 条码不存在 */
    BARCODE_NOT_EXIST(40000, "条码不存在"),
    ;

    private int key;
    private String value;

    ItemErrorCodeEnum(int key, String value) {
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