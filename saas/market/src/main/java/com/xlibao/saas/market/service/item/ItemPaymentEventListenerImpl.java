package com.xlibao.saas.market.service.item;

import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.saas.market.listener.PaymentEventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Transactional
@Component
public class ItemPaymentEventListenerImpl implements PaymentEventListener {

    @Override
    public void notifyPayment(OrderEntry order, String paymentType) {
    }
}