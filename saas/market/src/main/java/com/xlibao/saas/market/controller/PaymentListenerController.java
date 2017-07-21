package com.xlibao.saas.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/3/6.
 */
@Controller
@RequestMapping(value = "/market/paymentListener")
public class PaymentListenerController {

    @Autowired
    private PaymentService paymentService;

    @ResponseBody
    @RequestMapping(value = "callbackBalancePayment")
    public JSONObject callbackBalancePayment() {
        return paymentService.notifyOrderBalancePayment();
    }

    @ResponseBody
    @RequestMapping(value = "callbackNativeAlipayPayment")
    public JSONObject callbackNativeAlipayPayment() {
        return paymentService.notifyNativeAlipayPayment();
    }

    @ResponseBody
    @RequestMapping(value = "callbackNativeWeixinAppPayment")
    public JSONObject callbackNativeWeixinAppPayment() {
        return paymentService.notifyNativeWeixinAppPayment();
    }

    @ResponseBody
    @RequestMapping(value = "callbackNativeWeixinJSPayment")
    public JSONObject callbackNativeWeixinJSPayment() {
        return paymentService.notifyNativeWeixinJSPayment();
    }
}