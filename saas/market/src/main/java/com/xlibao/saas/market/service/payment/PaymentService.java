package com.xlibao.saas.market.service.payment;

import com.alibaba.fastjson.JSONObject;

/**
 * @author chinahuangxc on 2017/7/10.
 */
public interface PaymentService {

    JSONObject notifyOrderBalancePayment();

    JSONObject notifyNativeAlipayPayment();

    JSONObject notifyNativeWeixinAppPayment();

    JSONObject notifyNativeWeixinJSPayment();
}