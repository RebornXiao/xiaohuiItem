package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.common.CommonUtils;
import com.xlibao.saas.market.data.model.*;
import com.xlibao.saas.market.service.item.ItemLockTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Component
public class ItemDataAccessManager {

    @Autowired
    private MarketItemMapper itemMapper;
    @Autowired
    private MarketItemLocationMapper itemLocationMapper;
    @Autowired
    private MarketItemDailyPurchaseLoggerMapper itemDailyPurchaseLoggerMapper;
    @Autowired
    private MarketItemStockLockLoggerMapper itemStockLockLoggerMapper;
    @Autowired
    private MarketShoppingCartMapper shoppingCartMapper;
    @Autowired
    private MarketSpecialButtonMapper specialButtonMapper;

    public List<MarketItem> specialProducts(long marketId, long appointType, long timeout, int sortType, int sortValue, int pageStartIndex, int pageSize) {
        return itemMapper.specialProducts(marketId, appointType, timeout, sortType, sortValue, pageStartIndex, pageSize);
    }

    public List<MarketItem> conditionOrdering(long marketId, String itemTemplateSet, int sortType, int sortValue, int pageStartIndex, int pageSize) {
        return itemMapper.conditionOrdering(marketId, itemTemplateSet, sortType, sortValue, pageStartIndex, pageSize);
    }

    public List<MarketItem> getItemsForItemTemplateSet(long marketId, String itemTemplateSet, int pageStartIndex, int pageSize) {
        return itemMapper.getItemsForItemTemplateSet(marketId, itemTemplateSet, pageStartIndex, pageSize);
    }

    public List<MarketItem> getItemForItemTemplates(long marketId, String itemTemplateSet) {
        return itemMapper.getItemForItemTemplates(marketId, itemTemplateSet);
    }

    public List<MarketItem> getItems(String itemSet) {
        return itemMapper.getItems(itemSet);
    }

    public int lockItemStock(long itemId, int lockQuantity) {
        return itemMapper.lockItemStock(itemId, lockQuantity);
    }

    public int itemPending(long itemId, int quantity) {
        return itemMapper.itemPending(itemId, quantity);
    }

    public int incrementPending(long itemId, int quantity) {
        return itemMapper.incrementPending(itemId, quantity);
    }

    public List<MarketItemLocation> getItemLocations(long itemId) {
        return itemLocationMapper.getItemLocations(itemId);
    }

    public int updateItemLocationStock(long locationId, int decrementStock) {
        return itemLocationMapper.updateItemLocationStock(locationId, decrementStock);
    }

    public List<MarketSpecialButton> getButtons() {
        return specialButtonMapper.getButtons();
    }

    public List<MarketItemDailyPurchaseLogger> passportDailyBuyLoggers(long passportId, String itemSet) {
        return itemDailyPurchaseLoggerMapper.passportDailyBuyLoggers(passportId, itemSet, CommonUtils.todayForCH());
    }

    public int createItemStockLogger(MarketItemStockLockLogger itemStockLockLogger) {
        return itemStockLockLoggerMapper.createItemStockLogger(itemStockLockLogger);
    }

    public List<MarketItemStockLockLogger> getItemStockLockLoggers(String orderSequenceNumber, ItemLockTypeEnum itemLockTypeEnum, int status) {
        return itemStockLockLoggerMapper.getItemStockLockLoggers(orderSequenceNumber, itemLockTypeEnum.getKey(), status);
    }
}