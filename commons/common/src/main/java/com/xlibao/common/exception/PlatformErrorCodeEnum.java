package com.xlibao.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;

/**
 * @author chinahuangxc on 2017/7/25.
 */
public enum PlatformErrorCodeEnum {

    /** 99 -- 服务器无响应 */
    REMOTE_TIMEOUT(99, "远程服务器无响应"),
    /** 100 -- 没有更多数据 */
    NO_MORE_DATA(100, "没有更多数据"),
    /** 200 -- 错误的手机号 */
    PHONE_NUMBER_ERROR(200, "错误的手机号"),
    /** 201 -- 应用设置拦截消息 */
    SETTING_ERROR(201,"应用设置拦截消息"),
    /** 999 -- 您的登录已过期或帐号存在泄漏风险，请重新登录 */
    INVALID_ACCESS(999, "您的登录已过期或帐号存在泄漏风险，请重新登录"),

    /** 300 -- 签名验证失败 */
    SIGN_ERROR(300, "签名验证失败"),
    /** 400 -- 找不到目标 */
    NOT_FOUND_TARGET(400, "找不到目标"),
    ;

    private int key;
    private String value;

    PlatformErrorCodeEnum(int key, String value) {
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