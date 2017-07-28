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

    public int createOrderStatusLogger(String orderSequenceNumber, OrderStatusEnum localStatus, OrderStatusEnum remoteStatus, long remoteCompleteTime) {
        MarketOrderStatusLogger orderStatusLogger = new MarketOrderStatusLogger();
        orderStatusLogger.setOrderSequenceNumber(orderSequenceNumber);

        orderStatusLogger.setLocalStatus(localStatus.getKey());
        orderStatusLogger.setLocalCompleteTime(new Date(System.currentTimeMillis()));
        orderStatusLogger.setRemoteStatus(remoteStatus.getKey());
        orderStatusLogger.setRemoteCompleteTime(new Date(remoteCompleteTime));
        return orderStatusLoggerMapper.createOrderStatusLogger(orderStatusLogger);
    }

    public MarketOrderStatusLogger getOrderStatusLogger(String orderSequenceNumber, OrderStatusEnum localStatus) {
        return orderStatusLoggerMapper.getOrderStatusLogger(orderSequenceNumber, localStatus.getKey());
    }
}