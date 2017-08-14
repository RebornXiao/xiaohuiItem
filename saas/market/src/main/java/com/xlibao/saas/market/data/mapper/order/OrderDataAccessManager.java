package com.xlibao.saas.market.data.mapper.order;

import com.xlibao.common.constant.order.OrderStatusEnum;
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
        return orderStatusLoggerMapper.modifyOrderRemoteStatusLogger(orderSequenceNumber, notifyType, localStatus.getKey(), remoteStatus, remoteCompleteTime);
    }

    public MarketOrderStatusLogger getOrderStatusLogger(String orderSequenceNumber, OrderStatusEnum localStatus) {
        return orderStatusLoggerMapper.getOrderStatusLogger(orderSequenceNumber, localStatus.getKey());
    }
}