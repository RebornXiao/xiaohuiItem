package com.xlibao.saas.market.service.payment.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.service.payment.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("paymentService")
public class PaymentServiceImpl extends BasicWebService implements PaymentService {

    @Override
    public JSONObject notifyOrderBalancePayment() {
        return null;
    }
}