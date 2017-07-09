package com.xlibao.common.support;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/5/24.
 */
public class SharePaymentRemoteService {

    public static JSONObject fillBalanceParameter(String partnerId, String appId, String appKey, String prePaymentId, String randomParameter) {
        JSONObject parameters = new JSONObject();
        Map<String, String> signParameters = new HashMap<>();
        // 统一支付中心分配的合作商户ID
        parameters.put("partnerId", partnerId);
        signParameters.put("partnerId", partnerId);
        // 统一支付中心分配的AppId
        parameters.put("appId", appId);
        signParameters.put("appId", appId);
        // 统一支付中心进行余额支付时生成的预支付ID
        parameters.put("prePaymentId", prePaymentId);
        signParameters.put("prePaymentId", prePaymentId);
        // 随机参数，保证消息的安全性
        parameters.put("randomParameter", randomParameter);
        signParameters.put("randomParameter", randomParameter);
        // 当前时间戳 -- 毫秒
        parameters.put("timeStamp", System.currentTimeMillis());
        signParameters.put("timeStamp", String.valueOf(System.currentTimeMillis()));

        CommonUtils.fillSignature(signParameters, appKey);
        parameters.put("sign", signParameters.get("sign"));

        return parameters;
    }
}
