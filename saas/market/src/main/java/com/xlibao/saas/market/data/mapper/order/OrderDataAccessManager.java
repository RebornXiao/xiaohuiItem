package com.xlibao.saas.market.data.mapper.order;

import com.xlibao.common.constant.order.OrderStatusEnum;
import com.xlibao.saas.market.data.model.MarketOrderProperties;
import com.xlibao.saas.market.data.model.MarketOrderStatusLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Component
public class OrderDataAccessManager {

    @Autowired
    private MarketOrderStatusLoggerMapper orderStatusLoggerMapper;
    @Autowired
    private MarketOrderPropertiesMapper orderPropertiesMapper;

    public int createOrderStatusLogger(String orderSequenceNumber, int notifyType, OrderStatusEnum localStatus, int remoteStatus, long remoteCompleteTime) {
        MarketOrderStatusLogger orderStatusLogger = new MarketOrderStatusLogger();
        orderStatusLogger.setOrderSequenceNumber(orderSequenceNumber);
        orderStatusLogger.setNotifyType(notifyType);
        orderStatusLogger.setLocalStatus(localStatus.getKey());
        orderStatusLogger.setLocalCompleteTime(new Date(System.currentTimeMillis()));
        orderStatusLogger.setRemoteStatus(remoteStatus);
        orderStatusLogger.setRemoteCompleteTime(new Date(remoteCompleteTime));
        return orderStatusLoggerMapper.createOrderStatusLogger(orderStatusLogger);
    }

    public int modifyOrderRemoteStatusLogger(String orderSequenceNumber, int notifyType, OrderStatusEnum localStatus, int remoteStatus, long remoteCompleteTime) {
        return orderStatusLoggerMapper.modifyOrderRemoteStatusLogger(orderSequenceNumber, notifyType, localStatus.getKey(), remoteStatus, new Date(remoteCompleteTime));
    }

    public MarketOrderStatusLogger getOrderStatusLogger(String orderSequenceNumber, OrderStatusEnum localStatus) {
        return orderStatusLoggerMapper.getOrderStatusLogger(orderSequenceNumber, localStatus.getKey());
    }

    public MarketOrderProperties createOrderProperties(long orderId, String orderSequenceNumber, int propertiesType, String propertiesKey, String propertiesValue) {
        MarketOrderProperties orderProperties = new MarketOrderProperties();
        orderProperties.setOrderId(orderId);
        orderProperties.setOrderSequenceNumber(orderSequenceNumber);
        orderProperties.setType(propertiesType);
        orderProperties.setK(propertiesKey);
        orderProperties.setV(propertiesValue);

        orderPropertiesMapper.createOrderProperties(orderProperties);
        return orderProperties;
    }

    public MarketOrderProperties getOrderProperties(long orderId, int propertiesType, String propertiesKey) {
        return orderPropertiesMapper.getOrderProperties(orderId, propertiesType, propertiesKey);
    }

    public int updateOrderProperties(long id, String propertiesValue) {
        return orderPropertiesMapper.updateOrderProperties(id, propertiesValue);
    }
}