package com.xlibao.common.exception.code;

import com.xlibao.common.exception.XlibaoRuntimeException;

/**
 * <pre>
 *     <b>通行证错误码枚举</b>
 *     <b>注意：</b>设定范围为 -- 10000至19999
 * </pre>
 *
 * @author chinahuangxc on 2017/8/17.
 */
public enum PassportErrorCodeEnum {


    ;

    private int key;
    private String value;

    PassportErrorCodeEnum(int key, String value) {
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
