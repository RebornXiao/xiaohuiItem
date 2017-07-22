package com.xlibao.saas.market.service.payment.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("paymentService")
public class PaymentServiceImpl extends BasicWebService implements PaymentService {

    @Autowired
    private DataAccessFactory dataAccessFactory;

    @Override
    public JSONObject notifyOrderBalancePayment() {
        return null;
    }

    @Override
    public JSONObject notifyNativeAlipayPayment() {
        return null;
    }

    @Override
    public JSONObject notifyNativeWeixinAppPayment() {
        return null;
    }

    @Override
    public JSONObject notifyNativeWeixinJSPayment() {
        return null;
    }
}