package com.xlibao.saas.market.service.item;

import com.xlibao.common.CommonUtils;
import com.xlibao.common.constant.order.OrderTypeEnum;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.metadata.order.OrderItemSnapshot;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketEntry;
import com.xlibao.saas.market.data.model.MarketItemLocation;
import com.xlibao.saas.market.data.model.MarketItemStockLockLogger;
import com.xlibao.saas.market.listener.OrderEventListener;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author chinahuangxc on 2017/2/28.
 */
@Transactional
@Component
public class ItemOrderEventListenerImpl implements OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ItemOrderEventListenerImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;

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
        // 推送给硬件进行拣货操作(缓存)
        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(orderEntry.getShippingPassportId());

        StringBuilder message = new StringBuilder().append("0001").append(orderEntry.getOrderSequenceNumber());
        if (!CommonUtils.isEmpty(itemStockLockLoggers)) { // 原来存在锁定的记录 进行解锁同时新增挂起数量
            for (MarketItemStockLockLogger itemStockLockLogger : itemStockLockLoggers) {
                // 对商品进行库存挂起操作
                dataAccessFactory.getItemDataAccessManager().itemPending(itemStockLockLogger.getItemId(), itemStockLockLogger.getLockQuantity());
                // 设定锁定记录为：出货状态
                dataAccessFactory.getItemDataAccessManager().modifyStockLockStatus(itemStockLockLogger.getId(), ItemStockLockStatusEnum.SHIPMENT.getKey());
                String msg = decrementItemLocationStock(orderEntry.getOrderSequenceNumber(), itemStockLockLogger.getItemId(), itemStockLockLogger.getItemTemplateId(), itemStockLockLogger.getLockQuantity());
                message.append(msg);
            }
            // 发送消息给硬件做出货操作
            MarketShopRemoteService.sendMessage(marketEntry.getPassportId(), message.toString());
            return;
        }
        // 如果本来没有锁定的记录 那么则只需要进行挂起
        List<OrderItemSnapshot> itemSnapshots = orderEntry.getItemSnapshots();
        for (OrderItemSnapshot itemSnapshot : itemSnapshots) {
            dataAccessFactory.getItemDataAccessManager().incrementPending(itemSnapshot.getItemId(), itemSnapshot.totalQuantity());
            String msg = decrementItemLocationStock(orderEntry.getOrderSequenceNumber(), itemSnapshot.getItemId(), itemSnapshot.getItemTemplateId(), itemSnapshot.totalQuantity());
            message.append(msg);
        }
        // 发送消息给硬件做出货操作
        MarketShopRemoteService.sendMessage(marketEntry.getPassportId(), message.toString());
    }

    private String decrementItemLocationStock(String orderSequenceNumber, long itemId, long itemTemplateId, int quantity) {
        List<MarketItemLocation> itemLocations = dataAccessFactory.getItemDataAccessManager().getItemLocations(itemId);

        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(itemTemplateId);
        // 组合硬件消息
        StringBuilder msgBuilder = new StringBuilder();
        for (MarketItemLocation itemLocation : itemLocations) {
            int decrementStock = quantity;
            if (itemLocation.getStock() < quantity) { // 库存不足时 将库存清空
                decrementStock = itemLocation.getStock();
            }
            int result = dataAccessFactory.getItemDataAccessManager().updateItemLocationStock(itemLocation.getId(), itemLocation.getStock());
            if (result > 0) { // 数据库执行成功后减少未出货数量
                quantity -= decrementStock;
            }
            // 商品位置 数量(16进制，4位 不足时前面补0)
            msgBuilder.append(itemLocation).append(CommonUtils.toHexString(decrementStock, 4, "0"));
            if (quantity <= 0) {
                break;
            }
        }
        if (quantity > 0) {
            logger.error("【" + orderSequenceNumber + "】严重问题，商品库存不足，商品ID：" + itemId + "(" + itemTemplate.getName() + ")，需要扣除数量：" + quantity);
            ItemErrorCodeEnum.ITEM_STOCK_NOT_ENOUGH.throwException();
        }
        return msgBuilder.toString();
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