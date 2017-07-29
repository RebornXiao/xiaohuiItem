package com.xlibao.saas.market.controller.openapi;

/**
 * @author chinahuangxc on 2017/7/29.
 */

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/7/26.
 */
@Controller
@RequestMapping(value = "/market/order/listener")
public class OrderEventListenerController {

    @Autowired
    private PaymentService paymentService;

    @ResponseBody
    @RequestMapping(value = "notifyPaymentOrder")
    public JSONObject notifyPaymentOrder() {
        // 接单通知
        return paymentService.notifyPaymentOrder();
    }
}