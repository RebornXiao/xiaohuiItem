package com.xlibao.saas.market.core.service.support;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.saas.market.core.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/8/19.
 */
public class MarketApplicationRemoteService extends BasicRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(MarketApplicationRemoteService.class);

    public static JSONObject notifyShipment(String orderSequenceNumber, String serialNumber) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("orderSequenceNumber", orderSequenceNumber);
        parameters.put("serialNumber", serialNumber);

        String url = ConfigFactory.getDomainName().marketRemoteURL + "market/message/callback/notifyShipment.do";
        JSONObject response = executor(url, parameters);

        logger.info(orderSequenceNumber + "[" + serialNumber + "]通知商店取货结果：" + response);
        return response;
    }

    public static JSONObject notifyShelvesData(long passportId, String content) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("passportId", String.valueOf(passportId));
        parameters.put("content", content);

        String url = ConfigFactory.getDomainName().marketRemoteURL + "market/message/callback/notifyShelvesData.do";
        JSONObject response = executor(url, parameters);

        logger.info(passportId + " -- 通知商店货架信息结果：" + response);
        return response;
    }

    public static boolean askOrderPickUp(long passportId, String orderSequenceNumber) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("passportId", String.valueOf(passportId));
        parameters.put("orderSequenceNumber", orderSequenceNumber);

        String url = ConfigFactory.getDomainName().marketRemoteURL + "market/message/callback/askOrderPickUp.do";
        try {
            executor(url, parameters);
        } catch (Exception ex) {
            if (ex instanceof XlibaoRuntimeException) {
                if (((XlibaoRuntimeException) ex).getCode() != -1) {
                    return false;
                }
            }
            logger.error("询问是否可进行获取时发生了异常，此时因不知处于什么状态，先按正常处理(即通知硬件出货)；异常信息：" + ex.getMessage(), ex);
        }
        return true;
    }
}