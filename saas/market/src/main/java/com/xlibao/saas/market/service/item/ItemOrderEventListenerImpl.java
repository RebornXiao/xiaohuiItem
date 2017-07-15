package com.xlibao.saas.market.service.item;

import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.saas.market.data.mapper.item.ItemDataAccessManager;
import com.xlibao.saas.market.listener.OrderEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/2/28.
 */
@Transactional
@Component
public class ItemOrderEventListenerImpl implements OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ItemOrderEventListenerImpl.class);

    @Autowired
    private ItemDataAccessManager itemDataAccessManager;

    @Override
    public void notifyCreatedOrder(OrderEntry order) {
    }

    @Override
    public void notifyOrderPayment(OrderEntry order) {
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