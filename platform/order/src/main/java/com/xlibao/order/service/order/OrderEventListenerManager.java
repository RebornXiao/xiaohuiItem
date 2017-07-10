package com.xlibao.order.service.order;

import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.order.listener.OrderEventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/2/20.
 */
@Component
public class OrderEventListenerManager {

    private Map<String, OrderEventListener> orderEventListenerMap = new HashMap<>();

    public void registerOrderEventListener(String serviceName, OrderEventListener orderEventListener) {
        orderEventListenerMap.put(serviceName, orderEventListener);
    }

    public void notifyCreatedOrderEntry(OrderEntry orderEntry) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyCreatedOrderEntry(orderEntry);
        }
    }

    public void notifyCanceledOrderEntry(OrderEntry orderEntry, int beforeStatus, boolean isAuto) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyCanceledOrderEntry(orderEntry, beforeStatus, isAuto);
        }
    }

    public void notifyPaymentedOrderEntry(OrderEntry orderEntry, int beforeStatus) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyPaymentedOrderEntry(orderEntry, beforeStatus);
        }
    }

    public void notifyPushedOrderEntry(OrderEntry orderEntry, int pushType, String title, String content, byte write, String... targets) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyPushedOrderEntry(orderEntry, pushType, title, content, write, targets);
        }
    }

    public void notifyAcceptedOrderEntry(OrderEntry orderEntry, int beforeStatus) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyAcceptedOrderEntry(orderEntry, beforeStatus);
        }
    }

    public void notifyDistributionedOrderEntry(OrderEntry orderEntry, int beforeStatus) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyDistributionedOrderEntry(orderEntry, beforeStatus);
        }
    }

    public void notifyArrivedOrderEntry(OrderEntry orderEntry, int beforeStatus) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyArrivedOrderEntry(orderEntry, beforeStatus);
        }
    }

    public void notifyConfirmedOrderEntry(OrderEntry orderEntry, int beforeStatus) {
        for (OrderEventListener orderEventListener : orderEventListenerMap.values()) {
            orderEventListener.notifyConfirmedOrderEntry(orderEntry, beforeStatus);
        }
    }
}