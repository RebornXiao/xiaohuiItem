package com.xlibao.common.support;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/3/26.
 */
public class BasicRemoteService {

    protected static Map<String, String> initialParameter(String partnerId, String appId) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("partnerId", partnerId);
        parameters.put("appId", appId);
        return parameters;
    }

    protected static JSONObject postOrderMsg(String url, String appkey, Map<String, String> parameters) {
        // 填充签名参数 参数key为：sign 参与的密钥字段名：key
        CommonUtils.fillSignature(parameters, appkey);
        String data = HttpRequest.post(url, parameters);
        JSONObject response = JSONObject.parseObject(data);
        int code = response.getIntValue("code");
        if (code != 0) {
            throw new XlibaoRuntimeException(code, response.getString("msg"));
        }
        return response;
    }
}