package com.xlibao.saas.market.service.support.remote;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.DefineRandom;
import com.xlibao.common.constant.payment.PaymentTypeEnum;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.common.support.SharePaymentRemoteService;
import com.xlibao.saas.market.config.ConfigFactory;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/2/26.
 */
public class PaymentRemoteService extends BasicWebService {

    private static final Logger logger = Logger.getLogger(PaymentRemoteService.class);

    private static final Map<String, String> CALLBACK_URL = new HashMap<>();

    static {
        CALLBACK_URL.put(PaymentTypeEnum.BALANCE.getKey(), "market/payment/openapi/callbackBalancePayment");
        CALLBACK_URL.put(PaymentTypeEnum.ALIPAY.getKey(), "market/payment/openapi/callbackNativeAlipayPayment");
        CALLBACK_URL.put(PaymentTypeEnum.WEIXIN_NATIVE.getKey(), "market/payment/openapi/callbackNativeWeixinAppPayment");
        CALLBACK_URL.put(PaymentTypeEnum.WEIXIN_JS.getKey(), "market/payment/openapi/callbackNativeWeixinJSPayment");
    }

    public static JSONObject paymentOrder(long passportId, long roleId, String partnerTradeNumber, String paymentType, String transType, long transUnitAmount, int transNumber, long transTotalAmount, String transTitle, String remark, byte useCoupon, long discountAmount, String extendParameter) {
        String randomParameter = DefineRandom.randomString(32);

        Map<String, String> signParameters = new HashMap<>();

        signParameters.put("partnerId", ConfigFactory.getXMarketConfig().getPartnerId());
        signParameters.put("appId", ConfigFactory.getXMarketConfig().getPaymentAppId());
        signParameters.put("passportId", String.valueOf(passportId));
        signParameters.put("paymentType", paymentType);
        signParameters.put("transType", transType);
        signParameters.put("partnerUserId", String.valueOf(roleId));
        signParameters.put("partnerTradeNumber", partnerTradeNumber);
        signParameters.put("transUnitAmount", String.valueOf(transUnitAmount));
        signParameters.put("transNumber", String.valueOf(transNumber));
        signParameters.put("transTotalAmount", String.valueOf(transTotalAmount));
        signParameters.put("transTitle", transTitle);
        signParameters.put("remark", remark);
        signParameters.put("useCoupon", String.valueOf(useCoupon));
        signParameters.put("discountAmount", String.valueOf(discountAmount));
        signParameters.put("notifyUrl", ConfigFactory.getDomainNameConfig().marketRemoteURL + CALLBACK_URL.get(paymentType));
        signParameters.put("randomParameter", randomParameter);
        signParameters.put("extendParameter", extendParameter);

        CommonUtils.fillSignature(signParameters, ConfigFactory.getXMarketConfig().getPaymentAppkey());

        String parameter = HttpRequest.post(ConfigFactory.getDomainNameConfig().paymentRemoteURL + "paymentController/unifiedOrder", signParameters);

        JSONObject response = JSONObject.parseObject(parameter);

        logger.info("请求支付订单结果：" + response);

        if (response.getIntValue("code") == 0 && PaymentTypeEnum.BALANCE.getKey().equals(paymentType)) {
            return success(fillBalanceParameter(response.getJSONObject("response").getString("prePaymentId"), DefineRandom.randomChar(32)));
        }
        return response;
    }

    private static JSONObject fillBalanceParameter(String prePaymentId, String randomParameter) {
        return SharePaymentRemoteService.fillBalanceParameter(ConfigFactory.getXMarketConfig().getPartnerId(), ConfigFactory.getXMarketConfig().getPaymentAppId(), ConfigFactory.getXMarketConfig().getPaymentAppkey(), prePaymentId, randomParameter);
    }
}