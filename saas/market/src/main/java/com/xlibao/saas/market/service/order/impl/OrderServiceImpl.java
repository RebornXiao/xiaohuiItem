package com.xlibao.saas.market.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalConstantConfig;
import com.xlibao.common.constant.device.DeviceTypeEnum;
import com.xlibao.common.constant.order.CollectingFeesEnum;
import com.xlibao.common.constant.order.OrderStatusEnum;
import com.xlibao.common.constant.order.OrderTypeEnum;
import com.xlibao.common.constant.order.PushTypeEnum;
import com.xlibao.common.exception.XlibaoIllegalArgumentException;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.service.PlatformErrorCodeEnum;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.metadata.order.OrderItemSnapshot;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketEntry;
import com.xlibao.saas.market.data.model.MarketItem;
import com.xlibao.saas.market.data.model.MarketItemDailyPurchaseLogger;
import com.xlibao.saas.market.service.item.ItemErrorCodeEnum;
import com.xlibao.saas.market.service.order.OrderService;
import com.xlibao.saas.market.service.support.ItemSupport;
import com.xlibao.saas.market.service.support.remote.OrderRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        OrderEntry order = fillOrderData(passportId, deviceType, currentLocation, collectingFees, receiptProvince, receiptCity, receiptDistrict, receiptAddress,
                String.valueOf(passportId), receiptNickName, receiptPhone, receiptLocation, marketEntry, remark, itemSnapshots);

        List<OrderEntry> orders = new ArrayList<>();
        orders.add(order);

        // generateOrder(sequenceNumber, passportId, orders);
        // TODO 下单成功后 进行商品库存锁定
        return success("订单创建成功");
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

        order.setShippingPassportId(marketEntry.getPassportId());
        order.setShippingNickName(marketEntry.getAdminName());
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

        order.setRemark(remark);

        order.setCurrentLocation(currentLocation);
        order.setCollectingFees(collectingFees);

        long actualPrice = 0;
        long totalPrice = 0;
        for (OrderItemSnapshot itemSnapshot : itemSnapshots) {
            actualPrice += itemSnapshot.getTotalPrice();
            totalPrice += (itemSnapshot.getNormalPrice() * (itemSnapshot.getNormalQuantity() + itemSnapshot.getDiscountQuantity()));
        }
        long discountPrice = totalPrice - actualPrice;
        order.setActualPrice(actualPrice + marketEntry.getDeliveryCost());
        order.setTotalPrice(totalPrice + marketEntry.getDeliveryCost());
        order.setDiscountPrice(discountPrice);
        order.setPriceLogger(discountPrice <= 0 ? "无优惠" : "订单优惠(折扣)：" + CommonUtils.formatNumber(discountPrice / 100f, "#.##") + "元");
        order.setDistributionFee(marketEntry.getDeliveryCost());

        order.setItemSnapshots(itemSnapshots);
        return order;
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