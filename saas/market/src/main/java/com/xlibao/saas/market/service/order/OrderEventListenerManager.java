package com.xlibao.saas.market.service.order;

import com.xlibao.saas.market.listener.OrderEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Transactional
@Component
public class OrderEventListenerManager {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventListenerManager.class);

    private static final Map<String, OrderEventListener> ORDER_EVENT_LISTENER_MAP = new LinkedHashMap<>();

    public void registerOrderEventListener(String name, OrderEventListener orderEventListener) {
        logger.info(name + "注册订单动作监听器");
        ORDER_EVENT_LISTENER_MAP.put(name, orderEventListener);
    }
}