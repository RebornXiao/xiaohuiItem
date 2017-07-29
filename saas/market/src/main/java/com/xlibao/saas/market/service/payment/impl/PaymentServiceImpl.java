package com.xlibao.saas.market.service.payment.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.constant.payment.TransStatusEnum;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.saas.market.config.ConfigFactory;
import com.xlibao.saas.market.service.order.OrderEventListenerManager;
import com.xlibao.saas.market.service.payment.PaymentService;
import com.xlibao.saas.market.service.support.remote.OrderRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("paymentService")
public class PaymentServiceImpl extends BasicWebService implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private OrderEventListenerManager orderEventListenerManager;

    @Override
    public JSONObject notifyPaymentOrder() {
        String data = getUTF("data");
        JSONObject parameters = JSONObject.parseObject(data);

        Map<String, String> p = new HashMap<>();
        for (String k : parameters.keySet()) {
            p.put(k, parameters.getString(k));
        }
        String remoteSign = p.remove("sign");
        // 验证签名 本地验证
        if (!CommonUtils.matchSignature(p, remoteSign, ConfigFactory.getXMarketConfig().getPaymentAppkey())) {
            return fail("签名验证失败");
        }
        String partnerId = parameters.getString("partnerId");
        int transStatus = parameters.getIntValue("transStatus");
        String paymentType = parameters.getString("paymentType");
        String partnerTradeNumber = parameters.getString("partnerTradeNumber");

        logger.info("通知订单支付结果，订单标志为：" + partnerTradeNumber + "，支付类型：" + paymentType + "，支付状态：" + transStatus + "，合作商户号：" + partnerId);

        if (!partnerId.equals(ConfigFactory.getXMarketConfig().getPartnerId())) {
            logger.error("非本商户订单[" + partnerTradeNumber + "]，错误的支付回调，我方商户号：" + ConfigFactory.getXMarketConfig().getPartnerId() + "；回调商户号：" + partnerId);
            return success("非法请求");
        }

        if ((transStatus & TransStatusEnum.TRADE_SUCCESSED_SERVER.getKey()) != TransStatusEnum.TRADE_SUCCESSED_SERVER.getKey()) {
            return success("success");
        }
        OrderEntry orderEntry = OrderRemoteService.getOrder(partnerTradeNumber);
        orderEntry.setPaymentType(paymentType);
        orderEventListenerManager.notifyOrderPayment(orderEntry);
        return success("success");
    }
}