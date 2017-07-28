package com.xlibao.saas.market.service.order;

import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.saas.market.listener.OrderEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/27.
 */
@Transactional
@Component
public class InternalOrderEventListenerImpl implements OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(InternalOrderEventListenerImpl.class);

    @Override
    public void notifyCreatedOrder(OrderEntry entry) {
    }

    @Override
    public void notifyOrderPayment(OrderEntry order) {
        // 推送给硬件进行拣货操作
    }

    @Override
    public void notifyDeliveredOrderEntry(OrderEntry orderEntry) {
    }

    @Override
    public void notifyConfirmedOrderEntry(OrderEntry orderEntry) {
    }

    @Override
    public void notifyCanceledOrderEntry(OrderEntry orderEntry) {
    }
}