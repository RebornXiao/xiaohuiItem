package com.xlibao.saas.market.service.item;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.constant.order.OrderTypeEnum;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.market.data.model.MarketItemLocation;
import com.xlibao.market.data.model.MarketItemStockLockLogger;
import com.xlibao.market.data.model.MarketSplitOrder;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.metadata.order.OrderItemSnapshot;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.listener.OrderEventListener;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/2/28.
 */
@Transactional
@Component
public class ItemOrderEventListenerImpl implements OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ItemOrderEventListenerImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;
    @Autowired
    private MarketShopRemoteService marketShopRemoteService;

    @Override
    public void notifyCreatedOrder(OrderEntry orderEntry) {
        if (orderEntry.getType() != OrderTypeEnum.SALE_ORDER_TYPE.getKey()) {
            return;
        }
        logger.info("订单建立完成，正在锁定库存，订单序列号为：" + orderEntry.getOrderSequenceNumber());

        List<OrderItemSnapshot> itemSnapshots = orderEntry.getItemSnapshots();
        for (OrderItemSnapshot itemSnapshot : itemSnapshots) {
            logger.info("正在对订单[" + orderEntry.getOrderSequenceNumber() + "]的商品进行锁定操作，当前商品：" + itemSnapshot.getItemName() + "(" + itemSnapshot.getItemId() + ")；锁定数量：" + itemSnapshot.totalQuantity());
            // 锁定库存
            int result = dataAccessFactory.getItemDataAccessManager().lockItemStock(itemSnapshot.getItemId(), itemSnapshot.totalQuantity());
            if (result <= 0) {
                // 干脆整批不锁定
                logger.error("【更新锁定库存】对订单[" + orderEntry.getOrderSequenceNumber() + "]的商品进行锁定操作时发生了错误，当前商品：" + itemSnapshot.getItemName() + "(" + itemSnapshot.getItemId() + ")；锁定数量：" + itemSnapshot.totalQuantity());
                throw new XlibaoRuntimeException("锁定商品库存失败");
            }
            MarketItemStockLockLogger itemStockLockLogger = new MarketItemStockLockLogger();
            itemStockLockLogger.setOrderSequenceNumber(orderEntry.getOrderSequenceNumber());
            itemStockLockLogger.setItemId(itemSnapshot.getItemId());
            itemStockLockLogger.setItemTemplateId(itemSnapshot.getItemTemplateId());
            itemStockLockLogger.setLockQuantity(itemSnapshot.totalQuantity());
            itemStockLockLogger.setLockType(ItemLockTypeEnum.CREATE_ORDER.getKey());
            itemStockLockLogger.setLockStatus(ItemStockLockStatusEnum.LOCK.getKey());
            itemStockLockLogger.setLockTime(new Date());
            result = dataAccessFactory.getItemDataAccessManager().createItemStockLogger(itemStockLockLogger);
            if (result <= 0) {
                // 干脆整批不锁定
                logger.error("【添加锁定记录】对订单[" + orderEntry.getOrderSequenceNumber() + "]的商品进行锁定操作时发生了错误，当前商品：" + itemSnapshot.getItemName() + "(" + itemSnapshot.getItemId() + ")；锁定数量：" + itemSnapshot.totalQuantity());
                throw new XlibaoRuntimeException("锁定商品库存失败");
            }
        }
    }

    @Override
    public void notifyOrderPayment(OrderEntry orderEntry) {
        List<MarketItemStockLockLogger> itemStockLockLoggers = dataAccessFactory.getItemDataAccessManager().getItemStockLockLoggers(orderEntry.getOrderSequenceNumber(), ItemLockTypeEnum.CREATE_ORDER, ItemStockLockStatusEnum.LOCK.getKey());

        sendRemoteMessage(orderEntry);

        if (CommonUtils.isEmpty(itemStockLockLoggers)) {
            return;
        }
        // 原来存在锁定的记录 进行解锁
        for (MarketItemStockLockLogger itemStockLockLogger : itemStockLockLoggers) {
            // 设定锁定记录为：出货状态
            dataAccessFactory.getItemDataAccessManager().modifyStockLockStatus(itemStockLockLogger.getId(), ItemStockLockStatusEnum.SHIPMENT.getKey());
        }
    }

    private void sendRemoteMessage(OrderEntry orderEntry) {
        List<MarketSplitOrder> splitOrders = dataAccessFactory.getOrderDataAccessManager().getSplitOrders(orderEntry.getId());
        Map<String, String> messages = new HashMap<>();
        for (MarketSplitOrder splitOrder : splitOrders) {
            StringBuilder message = new StringBuilder().append(orderEntry.getOrderSequenceNumber()).append(splitOrder.getSerialCode());
            int locationCount = 0;

            JSONArray itemSet = JSONObject.parseArray(splitOrder.getItemSet());
            for (int i = 0; i < itemSet.size(); i++) {
                JSONObject data = itemSet.getJSONObject(i);

                long itemId = data.getLongValue("itemId");
                long itemTemplateId = data.getLongValue("itemTemplateId");
                int quantity = data.getIntValue("quantity");

                dataAccessFactory.getItemDataAccessManager().decrementItemStock(itemId, quantity);

                String[] msg = decrementItemLocationStock(orderEntry.getOrderSequenceNumber(), itemId, itemTemplateId, quantity);
                message.append(msg[0]);

                locationCount += Integer.parseInt(msg[1]);
            }
            message.append(CommonUtils.toHexString(locationCount, 4, "0"));
            messages.put(orderEntry.getOrderSequenceNumber() + CommonUtils.SPLIT_UNDER_LINE + splitOrder.getSerialCode(), message.toString());
        }
        // 推送给硬件进行拣货操作(缓存)
        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(orderEntry.getShippingPassportId());
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            // 发送消息给硬件做出货操作
            marketShopRemoteService.shipmentMessage(marketEntry.getPassportId(), entry.getKey(), entry.getValue());
        }
    }

    private String[] decrementItemLocationStock(String orderSequenceNumber, long itemId, long itemTemplateId, int quantity) {
        List<MarketItemLocation> itemLocations = dataAccessFactory.getItemDataAccessManager().getItemLocations(itemId);

        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(itemTemplateId);
        // 组合硬件消息
        StringBuilder msgBuilder = new StringBuilder();
        int locationCount = 0;
        for (MarketItemLocation itemLocation : itemLocations) {
            int decrementStock = quantity;
            if (itemLocation.getStock() < quantity) { // 库存不足时 将库存清空
                decrementStock = itemLocation.getStock();
            }
            int result = dataAccessFactory.getItemDataAccessManager().offsetItemLocationStock(itemLocation.getId(), decrementStock);
            if (result <= 0) {
                continue;
            }
            // 涉及的位置 +1
            locationCount++;
            quantity -= decrementStock;
            // 商品位置 数量(16进制，4位 不足时前面补0)
            msgBuilder.append(itemLocation.getLocationCode()).append(CommonUtils.toHexString(decrementStock, 4, "0"));
            if (quantity <= 0) {
                break;
            }
        }
        if (quantity > 0) {
            logger.error("【" + orderSequenceNumber + "】严重问题，商品库存不足，商品ID：" + itemId + "(" + itemTemplate.getName() + ")，需要扣除数量：" + quantity);
            throw MarketItemErrorCodeEnum.ITEM_STOCK_NOT_ENOUGH.throwException();
        }
        return new String[]{msgBuilder.toString(), String.valueOf(locationCount)};
    }

    @Override
    public void notifyDeliveredOrderEntry(OrderEntry orderEntry) {
    }

    @Override
    public void notifyConfirmedOrderEntry(OrderEntry orderEntry) {
    }

    @Override
    public void notifyCanceledOrderEntry(OrderEntry orderEntry) {
    }
}