package com.xlibao.saas.market.shop.service.support.remote;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.saas.market.shop.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/8/13.
 */
public class MarketRemoteService extends BasicRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(MarketRemoteService.class);

    public static JSONObject notifyShipment(String orderSequenceNumber, String serialNumber) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("orderSequenceNumber", orderSequenceNumber);
        parameters.put("serialNumber", serialNumber);

        String url = ConfigFactory.getDomainNameConfig().marketRemoteURL + "market/message/callback/notifyShipment.do";
        JSONObject response = executor(url, parameters);

        logger.info(orderSequenceNumber + "[" + serialNumber + "]通知商店取货结果：" + response);
        return response;
    }

    public static JSONObject notifyShelvesData(long passportId, String content) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("passportId", String.valueOf(passportId));
        parameters.put("content", content);

        String url = ConfigFactory.getDomainNameConfig().marketRemoteURL + "market/message/callback/notifyShelvesData.do";
        JSONObject response = executor(url, parameters);

        logger.info(passportId + " -- 通知商店货架信息结果：" + response);
        return response;
    }
}