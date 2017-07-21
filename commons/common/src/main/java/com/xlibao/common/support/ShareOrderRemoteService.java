package com.xlibao.common.support;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.metadata.order.OrderEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author chinahuangxc on 2017/4/10.
 */
public class ShareOrderRemoteService extends BasicRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(ShareOrderRemoteService.class);

    public static OrderEntry getOrder(String partnerId, String appId, String appkey, String urlPrefix, long orderId) {
        Map<String, String> parameters = initialParameter(partnerId, appId);

        parameters.put("orderId", String.valueOf(orderId));

        JSONObject response = postOrderMsg(urlPrefix + "order/getOrder", appkey, parameters);

        logger.info("获取订单数据：" + response);
        return JSONObject.parseObject(response.getString("response"), OrderEntry.class);
    }

    public static OrderEntry getOrder(String partnerId, String appId, String appkey, String urlPrefix, String orderSequenceNumber) {
        Map<String, String> parameters = initialParameter(partnerId, appId);

        parameters.put("orderSequenceNumber", orderSequenceNumber);

        JSONObject response = postOrderMsg(urlPrefix + "order/getOrderForSequenceNumber", appkey, parameters);

        logger.info("获取订单数据：" + response);
        return JSONObject.parseObject(response.getString("response"), OrderEntry.class);
    }
}
