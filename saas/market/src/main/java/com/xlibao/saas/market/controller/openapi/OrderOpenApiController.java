package com.xlibao.saas.market.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/8/10.
 */
@Controller
@RequestMapping(value = "/market/order/openapi")
public class OrderOpenApiController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "showOrders")
    public JSONObject showOrders() {
        return orderService.showOrders();
    }

    @ResponseBody
    @RequestMapping(value = "orderDetail")
    public JSONObject orderDetail() {
        return orderService.orderDetail();
    }

    /**
     * <pre>
     *     <b>预下单</b>
     *
     *     前端在进行下单前必须先获得预下单序号，建议在进入"确认订单"界面时发起预下单请求；
     *     每个序号有效期为5分钟，5分钟如果未完成下单操作，那么多次请求均为同一序号；
     *     若5分钟内下单后重新请求，则生成新的序号，该过程主要保护用户不会进行重复下单。
     *
     *     <b>访问地址：</b>http://domainName/market/order/openapi/prepareCreateOrder
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>passportId</b> - long 下单通行证ID，必填参数；未登录时，提示用户登录。
     *          <b>orderType</b> - int 订单类型，必填参数；具体参考：{@linkplain com.xlibao.common.constant.order.OrderTypeEnum}。
     *
     *     <b>返回结果：</b>
     *          <b>sequenceNumber</b> - String 预下单序列号
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "prepareCreateOrder")
    public JSONObject prepareCreateOrder() {
        return orderService.prepareCreateOrder();
    }

    /**
     * <pre>
     *     <b>生成订单</b>
     *
     *     <b>访问地址：</b>http://domainName/market/order/openapi/generateOrder
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>passportId</b> - long 下单通行证ID，必填参数。
     *          <b>marketId</b> - long 商店ID，必填参数。
     *          <b>deviceType</b> - int 设备类型，非必填参数；默认为{@linkplain com.xlibao.common.constant.device.DeviceTypeEnum#DEVICE_TYPE_ANDROID}，
     *                                              具体参考：{@linkplain com.xlibao.common.constant.device.DeviceTypeEnum}。
     *          <b>sequenceNumber</b> - String 预下单序号，必填参数。
     *          <b>currentLocation</b> - String 当前位置信息，非必填参数；尽量提供，方便后期跟踪；格式为：latitude,longitude。
     *          <b>collectingFees</b> - byte 代收费用，非必填参数；默认为{@linkplain com.xlibao.common.constant.order.CollectingFeesEnum#UN_COLLECTION} 不代收。
     *          <b>receiptProvince</b> - String 收货省份，必填参数。
     *          <b>receiptCity</b> - String 收货城市，必填参数。
     *          <b>receiptDistrict</b> - String 收货区域，必填参数
     *          <b>receiptAddress</b> - String 具体收货地址，必填参数。
     *          <b>receiptNickName</b> - String 收货人昵称，必填参数。
     *          <b>receiptPhone</b> - String 收货人联系号码，必填参数。
     *          <b>receiptLocation</b> - String 收货地址经纬度，非必填参数；请尽量提供，方便距离跟踪；格式为：latitude,longitude。
     *          <b>remark</b> - String 描述内容(备注)。
     *          <b>itemTemplateSet</b> - String 商品集合，格式为：JSONObject -- {"10000":"2"} ID:数量。
     *
     *     <b>返回结果：</b> 仅返回成功或失败。
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "generateOrder")
    public JSONObject generateOrder() {
        return orderService.generateOrder();
    }

    /**
     * <pre>
     *     <b>支付订单</b>
     *
     *     该方法仅作为供应链的支付入口，具体的支付过程实际由支付中心进行。
     *
     *     <b>访问地址：</b>http://domainName/market/order/openapi/paymentOrder
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>passportId</b> - long 通行证ID，必填参数。
     *          <b>sequenceNumber</b> - String 订单序列号。
     *          <b>deliverType</b> - int 配送类型，非必填参数；参看：{@link com.xlibao.common.constant.order.DeliverTypeEnum}
     *          <b>paymentType</b> - String 支付类型，必填参数；参考：{@linkplain com.xlibao.common.constant.payment.PaymentTypeEnum}
     *
     *     <b>返回结果：</b>
     *
     *          当<b>paymentType</b>为{@linkplain com.xlibao.common.constant.payment.PaymentTypeEnum#BALANCE}时，返回：
     *              <b>partnerId</b> - String 合作商户ID(供应链基于平台的合作商户号)，以xlb908开头。
     *              <b>appId</b> - String 应用ID(平台分配给供应链系统的支付应用ID)，以908开头。
     *              <b>prePaymentId</b> - String 预支付ID，由支付中心生成。
     *              <b>randomParameter</b> - String 供应链本次支付过程生成的一个32位随机数
     *              <b>timeStamp</b> - long 服务器发起支付时的时间
     *              <b>sign</b> - String 签名参数
     *            请求方在获得以上参数后，将上述参数填充到JSONObject数据结构中，如：{"partnerId" : "partnerId", "appId" : "appId", "prePaymentId" : "prePaymentId", "randomParameter" : "randomParameter", "timeStamp" : "timeStamp", "sign" : "sign"}。
     *            然后将上述的JSONObject数据结果当成一个字符串(注意需要进行URLEncode.encode)填充到另一个JSONObject中，key为<b>paymentParameter</b>；连同
     *              <b>passportId</b> - long 通行证ID
     *              <b>paymentPassword</b> - String 支付密码
     *            向支付中心发起余额支付请求，地址为：http://paymentDomainName/payment/balancePayment
     *
     *           支付中心负责返回支付结果的成败
     *
     *          当<b>paymentType</b>为{@linkplain com.xlibao.common.constant.payment.PaymentTypeEnum#ALIPAY}时，返回：paymentURL(支付宝支付链接)
     *            前端直接将上述参数填充至支付宝提供的SDK中
     *
     *          当<b>paymentType</b>为{@linkplain com.xlibao.common.constant.payment.PaymentTypeEnum#WEIXIN_APP}时，返回：
     *              <b>appid</b> - String 微信分配的appId。
     *              <b>partnerid</b> - String 微信支付分配的商户号。
     *              <b>prepayid</b> - String 预支付交易会话ID prepayid，微信返回的支付交易会话ID。
     *              <b>package</b> - String 扩展字段 package 暂填写固定值Sign=WXPay
     *              <b>noncestr</b> - String 随机字符串 noncestr 不长于32位
     *              <b>timestamp</b> - int 时间戳
     *              <b>sign</b> - String MD5加密后的签名字符串
     *            前端直接将上述参数填充至微信提供的SDK中
     *
     *          当<b>paymentType</b>为{@linkplain com.xlibao.common.constant.payment.PaymentTypeEnum#WEIXIN_NATIVE}时，返回：
     *              <b>codeUrl</b> - String 二维码内容，前端将该内容通过第三方控件(亦可前端编码实现)将其显示为二维码
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "paymentOrder")
    public JSONObject paymentOrder() {
        return orderService.paymentOrder();
    }

    /**
     * <pre>
     *     <b>接单</b>
     *
     *     <b>访问地址：</b>http://domainName/market/order/openapi/acceptOrder
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "acceptOrder")
    public JSONObject acceptOrder() {
        return orderService.acceptOrder();
    }

    /**
     * <pre>
     *     <b>退款</b>
     *
     *     <b>访问地址：</b>http://domainName/market/order/openapi/refundOrder
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>passportId</b> - long 通行证ID，必填参数；对应发起退款的用户。
     *          <b>orderSequenceNumber</b> - String 订单序号，必填参数。
     *
     *     <b>返回：</b>仅返回成功或失败的描述
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "refundOrder")
    public JSONObject refundOrder() {
        return orderService.refundOrder();
    }
}