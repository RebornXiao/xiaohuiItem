package com.xlibao.saas.market.service.order.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.GlobalConstantConfig;
import com.xlibao.common.constant.device.DeviceTypeEnum;
import com.xlibao.common.constant.order.*;
import com.xlibao.common.constant.payment.TransTypeEnum;
import com.xlibao.common.exception.PlatformErrorCodeEnum;
import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.exception.code.OrderErrorCodeEnum;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.metadata.order.OrderItemSnapshot;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketEntry;
import com.xlibao.saas.market.data.model.MarketItem;
import com.xlibao.saas.market.data.model.MarketItemDailyPurchaseLogger;
import com.xlibao.saas.market.service.XMarketTimeConfig;
import com.xlibao.saas.market.service.item.ItemErrorCodeEnum;
import com.xlibao.saas.market.service.order.MarketOrderErrorCodeEnum;
import com.xlibao.saas.market.service.order.OrderEventListenerManager;
import com.xlibao.saas.market.service.order.OrderService;
import com.xlibao.saas.market.service.order.StatusEnterEnum;
import com.xlibao.saas.market.service.support.ItemSupport;
import com.xlibao.saas.market.service.support.remote.OrderRemoteService;
import com.xlibao.saas.market.service.support.remote.PaymentRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("orderService")
public class OrderServiceImpl extends BasicWebService implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;
    @Autowired
    private OrderEventListenerManager orderEventListenerManager;
    @Autowired
    private ItemSupport itemSupport;

    @Override
    public JSONObject prepareCreateOrder() {
        long passportId = getPassportId();
        int orderType = getIntParameter("orderType");
        OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeEnum(orderType);
        if (orderTypeEnum == null) {
            return fail("订单类型出错，无法预创建订单，类型值：" + orderType);
        }
        return OrderRemoteService.prepareCreateOrder(passportId, orderTypeEnum);
    }

    @Override
    public JSONObject generateOrder() {
        long passportId = getPassportId();
        long marketId = getLongParameter("marketId");
        int deviceType = getIntParameter("userSource", DeviceTypeEnum.DEVICE_TYPE_ANDROID.getKey());
        String sequenceNumber = getUTF("sequenceNumber");
        String currentLocation = getUTF("currentLocation", GlobalConstantConfig.INVALID_LOCATION);
        byte collectingFees = getByteParameter("collectingFees", CollectingFeesEnum.UN_COLLECTION.getKey());
        String receiptProvince = getUTF("receiptProvince", "");
        String receiptCity = getUTF("receiptCity", "");
        String receiptDistrict = getUTF("receiptDistrict", "");
        String receiptAddress = getUTF("receiptAddress");
        String receiptNickName = getUTF("receiptNickName");
        String receiptPhone = getUTF("receiptPhone");
        String receiptLocation = getUTF("receiptLocation", GlobalConstantConfig.INVALID_LOCATION);
        // 备注
        String remark = getUTF("remark", "");
        // 商品集合
        String itemTemplateSet = getUTF("itemTemplateSet");

        if (!CommonUtils.isMobileNum(receiptPhone)) {
            return PlatformErrorCodeEnum.PHONE_NUMBER_ERROR.response("手机号[" + receiptPhone + "]无效");
        }
        // 用户购买的商品信息
        JSONObject buyItemTemplates = JSONObject.parseObject(itemTemplateSet);
        if (buyItemTemplates.isEmpty()) {
            logger.error("下单时无法获取到购买的商品信息，passport id: " + passportId + "，sequenceNumber: " + sequenceNumber + " itemTemplateSet: " + itemTemplateSet);
            return ItemErrorCodeEnum.INVALID_ITEMS.response("下单失败，请选择有效的商品");
        }
        List<OrderItemSnapshot> itemSnapshots = generateItemSnapshots(passportId, marketId, buyItemTemplates);

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketId);

        OrderEntry orderEntry = fillOrderData(passportId, deviceType, currentLocation, collectingFees, receiptProvince, receiptCity, receiptDistrict, receiptAddress,
                String.valueOf(passportId), receiptNickName, receiptPhone, receiptLocation, marketEntry, remark, itemSnapshots);

        List<OrderEntry> orders = new ArrayList<>();
        orders.add(orderEntry);

        JSONArray orderEntries = OrderRemoteService.generateOrder(sequenceNumber, passportId, orders);
        // 获取最新订单数据
        orderEntry = JSONObject.toJavaObject(orderEntries.getJSONObject(0), OrderEntry.class);
        // 下单成功后 进行商品库存锁定
        orderEventListenerManager.notifyCreatedOrder(orderEntry);
        return success("订单创建成功");
    }

    @Override
    public JSONObject paymentOrder() {
        long passportId = getPassportId();
        String orderSequenceNumber = getUTF("orderSequenceNumber");
        String paymentType = getUTF("paymentType");
        int deliverType = getIntParameter("deliverType", DeliverTypeEnum.PICKED_UP.getKey());

        OrderEntry order = OrderRemoteService.getOrder(orderSequenceNumber);
        if ((order.getStatus() & OrderStatusEnum.ORDER_STATUS_PAYMENT.getKey()) == OrderStatusEnum.ORDER_STATUS_PAYMENT.getKey()) {
            throw new XlibaoRuntimeException("订单已支付！");
        }
        // 重新计价
        correctingOrderCost(passportId, deliverType, order);
        // 总共需要支付的费用
        long paymentAmount = order.getActualPrice();
        // 支付过程由订单中心执行
        return PaymentRemoteService.paymentOrder(passportId, passportId, orderSequenceNumber, paymentType, String.valueOf(TransTypeEnum.MARKET_PAYMENT.getKey()), paymentAmount, TransTypeEnum.PAYMENT.getKey(),
                paymentAmount, TransTypeEnum.PAYMENT.getValue(), "", GlobalAppointmentOptEnum.LOGIC_FALSE.getKey(), 0, "");
    }

    @Override
    public JSONObject showOrders() {
        long passportId = getPassportId();
        int roleType = getIntParameter("roleType");
        int orderType = getIntParameter("orderType");
        int statusEnter = getIntParameter("statusEnter");
        int pageIndex = getIntParameter("pageIndex", GlobalConstantConfig.DEFAULT_PAGE_INDEX);
        int pageSize = getPageSize();

        List<OrderEntry> orders = OrderRemoteService.showOrders(passportId, GlobalAppointmentOptEnum.LOGIC_FALSE.getKey(), roleType, orderStatusSet(roleType, statusEnter), orderType, pageIndex, pageSize);
        JSONArray response = fillShowOrderMsg(roleType, orders);
        return success(response);
    }

    @Override
    public JSONObject orderDetail() {
        long passportId = getPassportId();
        int roleType = getIntParameter("roleType");
        long orderId = getLongParameter("orderId");

        OrderEntry orderEntry = OrderRemoteService.getOrder(orderId);

        return fillOrderDetailMsg(orderEntry, roleType, passportId);
    }

    @Override
    public JSONObject pickUpItems() {
        long passportId = getLongParameter("passportId");
        String orderSequenceNumber = getUTF("orderSequenceNumber");

        OrderEntry orderEntry = OrderRemoteService.getOrder(orderSequenceNumber);

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarketForPassport(passportId);
        if (!Objects.equals(orderEntry.getShippingPassportId(), marketEntry.getId())) {
            // TODO 推送通知用户订单权限出错
            return MarketOrderErrorCodeEnum.NON_MARKET_ORDER.response();
        }
        if (orderEntry.getStatus() == OrderStatusEnum.ORDER_STATUS_DEFAULT.getKey()) {
            // TODO 推送通知用户订单未支付
            OrderErrorCodeEnum.UN_PAYMENT_ORDER.throwException();
        }
        if (orderEntry.getStatus() == OrderStatusEnum.ORDER_STATUS_DISTRIBUTION.getKey()) {
            // TODO 推送通知用户订单处于配送中
            OrderErrorCodeEnum.HAS_DISTRIBUTION_ORDER.throwException();
        }
        if (orderEntry.getStatus() == OrderStatusEnum.ORDER_STATUS_ARRIVE.getKey()) {
            // TODO 通知用户该订单已取货(已签收、已提货)
            OrderErrorCodeEnum.HAS_ARRIVE_ORDER.throwException();
        }
        if (orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey()) { // 自提
            if (passportId != Long.parseLong(orderEntry.getPartnerUserId()) && passportId != Long.parseLong(orderEntry.getReceiptUserId())) {
                // 通知用户权限出错
                return PlatformErrorCodeEnum.NOT_HAVE_PERMISSION.response();
            }
        }
        if (orderEntry.getDeliverType() == DeliverTypeEnum.DISTRIBUTION.getKey()) {
            if (passportId != orderEntry.getCourierPassportId()) {
                // 通知用户权限出错
                return PlatformErrorCodeEnum.NOT_HAVE_PERMISSION.response();
            }
        }
        return null;
    }

    @Override
    public JSONObject acceptOrder() {
        // 接单者
        long passportId = getLongParameter("passportId");
        // 订单ID
        long orderId = getLongParameter("orderId");

        OrderEntry orderEntry = OrderRemoteService.getOrder(orderId);
        if (orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey()) {
            return MarketOrderErrorCodeEnum.ORDER_STATUS_ERROR.response("不能接取该订单，用户设置为自提状态");
        }
        if (orderEntry.getCourierPassportId() != null && orderEntry.getCourierPassportId() > 0) {
            return MarketOrderErrorCodeEnum.ORDER_HAS_ACCEPT.response();
        }
        return OrderRemoteService.acceptOrder(passportId, orderId);
    }

    private String orderStatusSet(int roleType, int statusEnter) {
        if (roleType == OrderRoleTypeEnum.CONSUMER.getKey()) { // 消费者
            if (statusEnter == StatusEnterEnum.ALL.getKey()) { // 用户 全部
                return String.valueOf(0);
            }
            if (statusEnter == StatusEnterEnum.WAIT_PAYMENT.getKey()) { // 用户 待支付
                return String.valueOf(OrderStatusEnum.ORDER_STATUS_DEFAULT.getKey());
            }
            if (statusEnter == StatusEnterEnum.WAIT_RECEIPT.getKey()) { // 用户 待收货
                return String.valueOf(OrderStatusEnum.ORDER_STATUS_ARRIVE.getKey());
            }
            if (statusEnter == StatusEnterEnum.REFUND.getKey()) { // 用户 退款中
                return String.valueOf(OrderStatusEnum.ORDER_STATUS_REFUND.getKey());
            }
        }
        return OrderStatusEnum.ORDER_STATUS_CANCEL.getKey() + CommonUtils.SPLIT_COMMA + OrderStatusEnum.ORDER_STATUS_CONFIRM.getKey();
    }

    private JSONArray fillShowOrderMsg(int roleType, List<OrderEntry> orderEntries) {
        if (roleType != OrderRoleTypeEnum.CONSUMER.getKey() && roleType != OrderRoleTypeEnum.COURIER.getKey()) {
            return new JSONArray();
        }
        JSONArray response = new JSONArray();

        for (OrderEntry orderEntry : orderEntries) {
            JSONObject data = new JSONObject();

            MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(orderEntry.getShippingPassportId());
            data.put("marketId", marketEntry.getId());
            data.put("marketName", marketEntry.getName());
            data.put("marketFormatAddress", marketEntry.formatAddress());
            data.put("actualPrice", orderEntry.getActualPrice());
            data.put("orderStatus", orderEntry.getStatus());
            data.put("deliverStatus", orderEntry.getDeliverStatus());

            fillItemSnapshotMsg(data, orderEntry);
            response.add(data);
        }
        return response;
    }

    private JSONObject fillOrderDetailMsg(OrderEntry orderEntry, int roleType, long passportId) {
        if (roleType != OrderRoleTypeEnum.CONSUMER.getKey()) {
            return fail("您没有权限查看该订单详情");
        }
        if (Long.parseLong(orderEntry.getPartnerUserId()) != passportId && Long.parseLong(orderEntry.getReceiptUserId()) != passportId) {
            return fail("您没有权限查看该订单详情");
        }
        JSONObject orderMsg = new JSONObject();

        int orderStatus = orderEntry.getStatus();
        String statusValue = OrderStatusEnum.getOrderStatusEnum(orderStatus).getValue();

        orderMsg.put("orderId", orderEntry.getId());
        orderMsg.put("sequenceNumber", orderEntry.getSequenceNumber());
        orderMsg.put("orderSequenceNumber", orderEntry.getOrderSequenceNumber());
        orderMsg.put("createTime", CommonUtils.dateFormat(orderEntry.getCreateTime().getTime()));
        orderMsg.put("deliverType", orderEntry.getDeliverType());
        orderMsg.put("deliverValue", orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey() ? "到店自提" : "小惠配送");
        orderMsg.put("orderStatus", orderStatus);
        orderMsg.put("statusValue", statusValue);

        orderMsg.put("deliverTitle", orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey() ? "配送进度" : "自提进度");
        orderMsg.put("deliverResult", orderStatus == OrderStatusEnum.ORDER_STATUS_CONFIRM.getKey() ? "已签收" : "已取货");

        // 实收费用
        orderMsg.put("actualPrice", orderEntry.getActualPrice());
        // 配送费
        orderMsg.put("distributionFee", orderEntry.getDistributionFee());
        // 优惠费用
        orderMsg.put("discountPrice", orderEntry.getDiscountPrice());

        orderMsg.put("addressTitle", orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey() ? "取货地址：" : "收货地址：");
        orderMsg.put("address", orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey() ? orderEntry.formatReceiptAddress() : orderEntry.formatShippingAddress());
        orderMsg.put("targetTitle", orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey() ? "取货点：" : "收货人：");
        orderMsg.put("targetName", orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey() ? "小惠便利店" + orderEntry.getReceiptNickName() : orderEntry.getShippingNickName());

        fillItemSnapshotMsg(orderMsg, orderEntry);

        return success(orderMsg);
    }

    private void fillItemSnapshotMsg(JSONObject response, OrderEntry orderEntry) {
        int totalItemQuantity = 0;

        List<OrderItemSnapshot> itemSnapshots = orderEntry.getItemSnapshots();

        JSONArray items = new JSONArray();
        for (OrderItemSnapshot itemSnapshot : itemSnapshots) {
            long itemTemplateId = itemSnapshot.getItemTemplateId();

            ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(itemTemplateId);

            JSONObject item = new JSONObject();

            item.put("itemSnapshotId", itemSnapshot.getId());
            item.put("itemId", itemSnapshot.getItemId());
            item.put("itemTemplateId", itemSnapshot.getItemTemplateId());
            item.put("itemName", itemSnapshot.getItemName());
            item.put("image", itemTemplate == null ? "" : itemTemplate.getImageUrl());
            item.put("price", itemSnapshot.getNormalPrice());
            item.put("quantity", itemSnapshot.totalQuantity());

            items.add(item);

            totalItemQuantity += itemSnapshot.totalQuantity();
        }
        response.put("items", items);
        response.put("totalItemQuantity", totalItemQuantity);
    }

    private List<OrderItemSnapshot> generateItemSnapshots(long passportId, long marketId, JSONObject buyItemTemplates) {
        String itemTemplateSet = processItemTemplateSet(buyItemTemplates);

        List<MarketItem> items = dataAccessFactory.getItemDataAccessManager().getItemForItemTemplates(marketId, itemTemplateSet);

        String itemSet = processItemSet(items);
        List<MarketItemDailyPurchaseLogger> itemDailyPurchaseLoggers = dataAccessFactory.getItemDataAccessManager().passportDailyBuyLoggers(passportId, itemSet);
        // 购买资格检查
        itemSupport.buyQualifications(items, itemDailyPurchaseLoggers, buyItemTemplates);

        Map<Long, MarketItemDailyPurchaseLogger> itemDailyPurchaseLoggerMap = itemSupport.itemDailyPurchaseLoggerMap(itemDailyPurchaseLoggers);
        List<OrderItemSnapshot> itemSnapshots = new ArrayList<>();
        for (MarketItem item : items) {
            int buyCount = buyItemTemplates.getIntValue(String.valueOf(item.getItemTemplateId()));
            if (buyCount <= 0) {
                ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(item.getItemTemplateId());
                throw new XlibaoRuntimeException(ItemErrorCodeEnum.BUY_QUANTITY_ERROR.getKey(), "商品" + (itemTemplate == null ? "" : "[" + itemTemplate.getName() + "]") + "的购买数量必须大于0");
            }
            MarketItemDailyPurchaseLogger itemDailyPurchaseLogger = itemDailyPurchaseLoggerMap.get(item.getItemTemplateId());
            itemSnapshots.add(fillOrderItemSnapshot(passportId, item, itemDailyPurchaseLogger, buyCount));
        }
        return itemSnapshots;
    }

    private OrderEntry fillOrderData(long passportId, int deviceType, String currentLocation, byte collectingFees, String receiptProvince, String receiptCity, String receiptDistrict, String receiptAddress,
                                     String receiptUserId, String receiptNickName, String receiptPhone, String receiptLocation, MarketEntry marketEntry, String remark, List<OrderItemSnapshot> itemSnapshots) {
        OrderEntry order = new OrderEntry();

        String marketLocation = marketEntry.getLocation();

        order.setPartnerUserId(String.valueOf(passportId));
        order.setUserSource(deviceType);
        order.setPushType(PushTypeEnum.UN_PUSH.getKey());

        order.setShippingPassportId(marketEntry.getId());
        order.setShippingNickName(marketEntry.getName());
        order.setShippingProvince(marketEntry.getProvince());
        order.setShippingCity(marketEntry.getCity());
        order.setShippingDistrict(marketEntry.getDistrict());
        order.setShippingAddress(marketEntry.getAddress());
        order.setShippingPhone(marketEntry.getPhoneNumber());
        order.setShippingLocation(marketLocation);

        order.setReceiptUserId(receiptUserId);
        order.setReceiptNickName(receiptNickName);
        order.setReceiptProvince(receiptProvince);
        order.setReceiptCity(receiptCity);
        order.setReceiptDistrict(receiptDistrict);
        order.setReceiptAddress(receiptAddress);
        order.setReceiptPhone(receiptPhone);
        order.setReceiptLocation(receiptLocation);

        order.setStatus(OrderStatusEnum.ORDER_STATUS_DEFAULT.getKey());
        order.setDeliverType(DeliverTypeEnum.PICKED_UP.getKey());

        order.setRemark(remark);

        order.setCurrentLocation(currentLocation);
        order.setCollectingFees(collectingFees);

        long actualPrice = 0;
        long totalPrice = 0;
        for (OrderItemSnapshot itemSnapshot : itemSnapshots) {
            actualPrice += itemSnapshot.getTotalPrice();
            totalPrice += (itemSnapshot.getNormalPrice() * itemSnapshot.totalQuantity());
        }
        long discountPrice = totalPrice - actualPrice;
        order.setActualPrice(actualPrice);
        order.setTotalPrice(totalPrice);
        order.setDiscountPrice(discountPrice);
        order.setPriceLogger(discountPrice <= 0 ? "无优惠" : "订单优惠(折扣)：" + CommonUtils.formatNumber(discountPrice / 100f, "#.##") + "元");
        order.setDistributionFee(0L);

        order.setItemSnapshots(itemSnapshots);
        return order;
    }

    private void correctingOrderCost(long passportId, int deliverType, OrderEntry order) {
        long timeout = (order.getCreateTime().getTime() + XMarketTimeConfig.ORDER_INVALID_TIME) - System.currentTimeMillis();
        if (timeout <= 0) {
            OrderRemoteService.cancelOrder(order.getId(), order.getPartnerUserId(), order.getStatus(), "订单过期");
            orderEventListenerManager.notifyCanceledOrderEntry(order);
            throw new XlibaoRuntimeException("订单已过期，请重新下单！");
        }
        List<OrderItemSnapshot> orderItemSnapshots = order.getItemSnapshots();

        JSONObject buyItemTemplates = new JSONObject();
        Map<Long, OrderItemSnapshot> itemSnapshotMapperMap = new HashMap<>();
        for (OrderItemSnapshot itemSnapshot : orderItemSnapshots) {
            itemSnapshotMapperMap.put(itemSnapshot.getItemId(), itemSnapshot);
            buyItemTemplates.put(String.valueOf(itemSnapshot.getItemTemplateId()), itemSnapshot.totalQuantity());
        }
        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(order.getShippingPassportId());

        boolean isPriceProtectionTime = (System.currentTimeMillis() - XMarketTimeConfig.PRICE_PROTECTION_TIME) < order.getCreateTime().getTime();
        List<OrderItemSnapshot> itemSnapshots = isPriceProtectionTime ? orderItemSnapshots : generateItemSnapshots(passportId, marketEntry.getId(), buyItemTemplates);

        long actualPrice = 0;
        long totalPrice = 0;
        for (OrderItemSnapshot itemSnapshot : itemSnapshots) {
            OrderItemSnapshot snapshot = itemSnapshotMapperMap.get(itemSnapshot.getItemId());
            if (!isPriceProtectionTime && !snapshot.isMatch(itemSnapshot)) {
                // 修改商品快照属性 远程服务
                OrderRemoteService.modifyItemSnapshot(order.getId(), itemSnapshot.getItemId(), itemSnapshot.getNormalPrice(), itemSnapshot.getDiscountPrice(), itemSnapshot.getNormalQuantity(), itemSnapshot.getDiscountQuantity(), itemSnapshot.getTotalPrice());
            }
            actualPrice += itemSnapshot.getTotalPrice();
            totalPrice += (itemSnapshot.getNormalPrice() * (itemSnapshot.totalQuantity()));
        }
        long deliverCost = 0;
        if (deliverType == DeliverTypeEnum.DISTRIBUTION.getKey()) {
            deliverCost = marketEntry.getDeliveryCost();
        }
        // 仓库的配送费
        actualPrice += deliverCost;
        totalPrice += deliverCost;

        long discountPrice = totalPrice - actualPrice;
        if (!order.isPriceMatch(actualPrice, totalPrice, discountPrice, deliverCost, deliverType)) {
            OrderRemoteService.correctOrderPrice(order.getId(), actualPrice, totalPrice, discountPrice, deliverCost, deliverType);

            order.setActualPrice(actualPrice);
            order.setTotalPrice(totalPrice);
            order.setDiscountPrice(discountPrice);
            order.setDistributionFee(deliverCost);
            order.setDeliverType(deliverType);
        }
    }

    private OrderItemSnapshot fillOrderItemSnapshot(long passportId, MarketItem item, MarketItemDailyPurchaseLogger roleDailyBuyLogger, int thisBuyCount) {
        OrderItemSnapshot orderItemSnapshot = itemSupport.fillOrderItemSnapshot(item, roleDailyBuyLogger, thisBuyCount);

        orderItemSnapshot.setUserMark(String.valueOf(passportId));
        return orderItemSnapshot;
    }

    private String processItemTemplateSet(JSONObject items) {
        StringBuilder itemTemplateSet = new StringBuilder();
        for (String s : items.keySet()) {
            itemTemplateSet.append(s).append(CommonUtils.SPLIT_COMMA);
        }
        itemTemplateSet.deleteCharAt(itemTemplateSet.length() - 1);

        return itemTemplateSet.toString();
    }

    private String processItemSet(List<MarketItem> marketItems) {
        StringBuilder itemSet = new StringBuilder();
        for (MarketItem item : marketItems) {
            itemSet.append(item.getId()).append(CommonUtils.SPLIT_COMMA);
        }
        itemSet.deleteCharAt(itemSet.length() - 1);

        return itemSet.toString();
    }

    private long getPassportId() {
        // TODO 临时做法 需要修改为通过token获取(未有缓存支持)
        long passportId = getLongParameter("passportId", 0);
        if (passportId == 0) {
            throw new XlibaoIllegalArgumentException("请先登录！");
        }
        return passportId;
    }
}