package com.xlibao.saas.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * <pre>
     *     <b>预下单</b>
     *
     *     前端在进行下单前必须先获得预下单序号，建议在进入"确认订单"界面时发起预下单请求；
     *     每个序号有效期为5分钟，5分钟如果未完成下单操作，那么多次请求均为同一序号；
     *     若5分钟内下单后重新请求，则生成新的序号，该过程主要保护用户不会进行重复下单。
     *
     *     <b>访问地址：</b>http://domainName/order/prepareCreateOrder
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
     *     <b>访问地址：</b>http://domainName/order/generateOrder
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
}