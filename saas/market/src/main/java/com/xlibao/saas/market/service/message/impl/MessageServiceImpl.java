package com.xlibao.saas.market.service.message.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.constant.order.DeliverTypeEnum;
import com.xlibao.common.constant.order.OrderStatusEnum;
import com.xlibao.common.exception.PlatformErrorCodeEnum;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketOrderProperties;
import com.xlibao.saas.market.data.model.MarketOrderStatusLogger;
import com.xlibao.saas.market.service.market.ShelvesService;
import com.xlibao.saas.market.service.message.MessageService;
import com.xlibao.saas.market.service.order.OrderNotifyTypeEnum;
import com.xlibao.saas.market.service.order.properties.PropertiesKeyEnum;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import com.xlibao.saas.market.service.support.remote.OrderRemoteService;
import com.xlibao.saas.market.service.support.remote.PaymentRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/8/11.
 */
@Transactional
@Service("messageService")
public class MessageServiceImpl extends BasicWebService implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;
    @Autowired
    private ShelvesService shelvesService;
    @Autowired
    private MarketShopRemoteService marketShopRemoteService;

    @Override
    public JSONObject notifyShipment() {
        long passportId = getLongParameter("passportId");
        String orderSequenceNumber = getUTF("orderSequenceNumber");
        String serialNumber = getUTF("serialNumber");

        OrderEntry orderEntry = checkOrderAdministrators(orderSequenceNumber, passportId);

        MarketOrderProperties orderProperties = dataAccessFactory.getOrderDataAccessManager().getOrderProperties(orderEntry.getId(), PropertiesKeyEnum.CONTAINER_DATA.getTypeEnum().getKey(), PropertiesKeyEnum.CONTAINER_DATA.getKey());
        JSONObject containerData = JSONObject.parseObject(orderProperties.getV());
        // 需要的货架数量
        int containerCount = containerData.getIntValue("containerCount");
        // 已出货完成的副单记录
        JSONArray containers = containerData.getJSONArray("containers");

        if (!containers.contains(serialNumber)) {
            containers.add(serialNumber);
            dataAccessFactory.getOrderDataAccessManager().updateOrderProperties(orderProperties.getId(), containerData.toJSONString());
        }
        if (orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey() && containerCount == containers.size()) { // 自提订单时，出货完成将订单状态设置为配送中(展示则为：待取货)
            // 修改订单状态
            OrderRemoteService.distributionOrder(orderEntry.getId(), OrderStatusEnum.ORDER_STATUS_DISTRIBUTION.getKey(), GlobalAppointmentOptEnum.LOGIC_FALSE.getKey());
        }
        dataAccessFactory.getOrderDataAccessManager().modifyOrderRemoteStatusLogger(orderSequenceNumber + CommonUtils.SPLIT_UNDER_LINE + serialNumber, OrderNotifyTypeEnum.HARDWARE.getKey(), OrderStatusEnum.ORDER_STATUS_PAYMENT,
                GlobalAppointmentOptEnum.LOGIC_TRUE.getKey(), System.currentTimeMillis());

        marketShopRemoteService.orderDataMessage(passportId, orderSequenceNumber);
        return success();
    }

    @Override
    public JSONObject notifyShelvesData() {
        long passportId = getLongParameter("passportId");
        String content = getUTF("content");

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarketForPassport(passportId);

        shelvesService.builderShelvesData(marketEntry, content);
        return success();
    }

    @Override
    public JSONObject notifyOrderData() {
        // 店铺的通行证ID
        long passportId = getLongParameter("passportId");
        // 反馈的订单编号
        String orderSequenceNumber = getUTF("orderSequenceNumber");
        // 状态码 --------- 00表示订单还未执行
        //        --------- 01表示订单正在配送中
        //        --------- 10表示已完成
        String statusCode = getUTF("statusCode");
        // 预存货柜的编号，当订单未完成时，反馈00
        String containerCodeSet = getUTF("containerCodeSet");

        OrderEntry orderEntry = checkOrderAdministrators(orderSequenceNumber, passportId);

        if ("00".equals(containerCodeSet)) { // 订单未完成
            return success();
        }
        if ("00".equals(statusCode)) { // 未执行
            return success();
        }
        if ("01".equals(statusCode)) { // 正在配送中
            return success();
        }
        refreshOrderProperties(orderEntry, PropertiesKeyEnum.CONTAINER_SET, containerCodeSet);
        return success();
    }

    @Override
    public JSONObject notifyRefund() {
        // 店铺的通行证ID
        long passportId = getLongParameter("passportId");
        // 完成退货的订单编号
        String orderSequenceNumber = getUTF("orderSequenceNumber");
        // 状态码 --------- 00表示退货完成
        //        --------- 01表示退货发生了故障
        String statusCode = getUTF("statusCode");

        OrderEntry orderEntry = checkOrderAdministrators(orderSequenceNumber, passportId);
        if ("01".equals(statusCode)) {
            return success("机器故障，无法完成退货操作");
        }
        // 执行退款流程
        PaymentRemoteService.refund(Long.parseLong(orderEntry.getPartnerUserId()), orderSequenceNumber);
        return success();
    }

    @Override
    public JSONObject notifyPickUp() {
        // 店铺的通行证ID
        long passportId = getLongParameter("passportId");
        // 取货的单号
        String orderSequenceNumber = getUTF("orderSequenceNumber");
        // 状态码 --------- 00表示柜门打开
        //        --------- 01表示柜门打开发生了故障
        //        --------- 02表示还没有配送完成
        String statusCode = getUTF("statusCode");
        // 预存货柜的编号，当订单未完成时，反馈00
        String containerCode = getUTF("containerCode");

        // 订单记录
        OrderEntry orderEntry = checkOrderAdministrators(orderSequenceNumber, passportId);
        // 修改该状态的远程状态
        dataAccessFactory.getOrderDataAccessManager().modifyOrderRemoteStatusLogger(orderSequenceNumber, OrderNotifyTypeEnum.HARDWARE.getKey(), OrderStatusEnum.ORDER_STATUS_ARRIVE, GlobalAppointmentOptEnum.LOGIC_TRUE.getKey(), System.currentTimeMillis());
        if ("00".equals(containerCode)) {
            return fail("商品未配送完成，请稍等");
        }
        if ("01".equals(statusCode)) {
            return fail("柜门发生故障，请联系工作人员");
        }
        if ("02".equals(statusCode)) {
            return fail("商品未配送完成，请稍等");
        }
        // 刷新订单的取货货柜信息
        refreshOrderProperties(orderEntry, PropertiesKeyEnum.PICK_UP_CONTAINER_SET, containerCode);
        if (orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey()) {
            MarketOrderProperties orderProperties = dataAccessFactory.getOrderDataAccessManager().getOrderProperties(orderEntry.getId(), PropertiesKeyEnum.CONTAINER_DATA.getTypeEnum().getKey(), PropertiesKeyEnum.CONTAINER_DATA.getKey());
            JSONObject containerData = JSONObject.parseObject(orderProperties.getV());
            // 需要的货架数量
            int containerCount = containerData.getIntValue("containerCount");
            if (containerCode.length() / 2 >= containerCount) { // 完成取货时将订单设置为完成状态
                return OrderRemoteService.confirmOrder(orderEntry.getId(), Long.parseLong(orderEntry.getPartnerUserId()), GlobalAppointmentOptEnum.LOGIC_FALSE.getKey());
            }
        }
        return success();
    }

    @Override
    public JSONObject askOrderPickUp() {
        long passportId = getLongParameter("passportId");
        String orderSequenceNumber = getUTF("orderSequenceNumber");

        OrderEntry orderEntry = checkOrderAdministrators(orderSequenceNumber, passportId);

        MarketOrderStatusLogger orderStatusLogger = dataAccessFactory.getOrderDataAccessManager().getOrderStatusLogger(orderSequenceNumber, OrderStatusEnum.ORDER_STATUS_ARRIVE);
        if (orderStatusLogger == null) { // 记录请求取货状态
            dataAccessFactory.getOrderDataAccessManager().createOrderStatusLogger(orderSequenceNumber, OrderNotifyTypeEnum.HARDWARE.getKey(), OrderStatusEnum.ORDER_STATUS_ARRIVE, GlobalAppointmentOptEnum.LOGIC_FALSE.getKey(), System.currentTimeMillis());
        }
        MarketOrderProperties orderProperties = dataAccessFactory.getOrderDataAccessManager().getOrderProperties(orderEntry.getId(), PropertiesKeyEnum.CONTAINER_DATA.getTypeEnum().getKey(), PropertiesKeyEnum.CONTAINER_DATA.getKey());
        JSONObject containerData = JSONObject.parseObject(orderProperties.getV());
        // 需要的货架数量
        int containerCount = containerData.getIntValue("containerCount");

        // 获取订单的货柜记录，检查是否已全部出货
        MarketOrderProperties orderContainerSetProperties = dataAccessFactory.getOrderDataAccessManager().getOrderProperties(orderEntry.getId(), PropertiesKeyEnum.CONTAINER_SET.getTypeEnum().getKey(), PropertiesKeyEnum.CONTAINER_SET.getKey());
        if (orderContainerSetProperties == null || (orderContainerSetProperties.getV().length() / 2 < containerCount)) { // 未完成订单货柜信息
            // 获取订单货柜信息
            marketShopRemoteService.orderDataMessage(passportId, orderSequenceNumber);
            // 未获取订单货柜信息
            return fail("未出货完成，请稍后重试");
        }
        MarketOrderProperties pickUpContainerSetProperties = dataAccessFactory.getOrderDataAccessManager().getOrderProperties(orderEntry.getId(), PropertiesKeyEnum.PICK_UP_CONTAINER_SET.getTypeEnum().getKey(), PropertiesKeyEnum.PICK_UP_CONTAINER_SET.getKey());
        if (pickUpContainerSetProperties == null) { // 未存在取货记录 可继续执行取货操作
            return success();
        }
        if (pickUpContainerSetProperties.getV().length() >= orderContainerSetProperties.getV().length()) {
            return fail("全部商品已完成出货，不能重复取货");
        }
        return success();
    }

    private void refreshOrderProperties(OrderEntry orderEntry, PropertiesKeyEnum propertiesKey, String value) {
        MarketOrderProperties orderProperties = dataAccessFactory.getOrderDataAccessManager().getOrderProperties(orderEntry.getId(), propertiesKey.getTypeEnum().getKey(), propertiesKey.getKey());
        if (orderProperties == null) {
            try {
                // 建立订单存放的货柜信息
                dataAccessFactory.getOrderDataAccessManager().createOrderProperties(orderEntry.getId(), orderEntry.getOrderSequenceNumber(), propertiesKey.getTypeEnum().getKey(), propertiesKey.getKey(), value);
                return;
            } catch (Exception ex) {
                orderProperties = dataAccessFactory.getOrderDataAccessManager().getOrderProperties(orderEntry.getId(), propertiesKey.getTypeEnum().getKey(), propertiesKey.getKey());
                if (orderProperties == null) {
                    throw new XlibaoRuntimeException("没法收货订单货柜信息的填充，建立失败，但又无法获取最新记录，异常信息为：" + ex.getMessage(), ex);
                }
            }
        }
        if (!orderProperties.getV().equals(value)) {
            dataAccessFactory.getOrderDataAccessManager().updateOrderProperties(orderProperties.getId(), value);
        }
    }

    private OrderEntry checkOrderAdministrators(String orderSequenceNumber, long passportId) {
        OrderEntry orderEntry = OrderRemoteService.getOrder(orderSequenceNumber);
        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(orderEntry.getShippingPassportId());
        if (marketEntry.getPassportId() != passportId) {
            logger.error("错误的权限，该订单属于的商店为：" + marketEntry.getName() + "(管理员通行证ID：" + marketEntry.getPassportId() + ")；请求的通行证ID：" + passportId);
            throw PlatformErrorCodeEnum.NOT_HAVE_PERMISSION.throwException();
        }
        return orderEntry;
    }
}