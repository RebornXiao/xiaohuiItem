package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.common.CommonUtils;
import com.xlibao.common.thread.AsyncScheduledService;
import com.xlibao.market.data.model.*;
import com.xlibao.saas.market.data.model.MarketSearchHistory;
import com.xlibao.saas.market.service.item.ItemLockTypeEnum;
import com.xlibao.saas.market.service.item.PrepareActionStatusEnum;
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
    @Autowired
    private MarketPrepareActionMapper prepareActionMapper;
    @Autowired
    private MarketSearchHistoryMapper searchHistoryMapper;
    @Autowired
    private MarketItemLocationStockLoggerMapper itemLocationStockLoggerMapper;
    @Autowired
    private MarketItemLadderPriceMapper itemLadderPriceMapper;

    public int createItem(MarketItem item) {
        return itemMapper.createItem(item);
    }

    public MarketItem getItem(long marketId, long itemTemplateId) {
        return itemMapper.getItem(marketId, itemTemplateId);
    }

    public MarketItem getMarketItem(long id) {
        return itemMapper.getMarketItem(id);
    }


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

    public int updateItem(long itemId, long costPrice, long sellPrice, long marketPrice, long discountPrice, byte status, String description) {
        return itemMapper.updateItem(itemId, costPrice, sellPrice, marketPrice, discountPrice, status, description);
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

    public List<MarketItem> searchMarketItems(long marketId, String searchType, String searchKey, int pageStartIndex, int pageSize) {
        return itemMapper.searchMarketItems(marketId, searchType, searchKey, pageStartIndex, pageSize);
    }

    public int releaseItemLockQuantity(long itemId, int releaseLockQuantity) {
        return itemMapper.releaseItemLockQuantity(itemId, releaseLockQuantity);
    }

    public int decrementItemStock(long itemId, int quantity) {
        return itemMapper.decrementItemStock(itemId, quantity);
    }

    public int offShelves(long itemId, int quantity, int status) {
        return itemMapper.offShelves(itemId, quantity, status);
    }

    public List<Long> existItemTemplates(long marketId, String itemTemplateSet) {
        return itemMapper.existItemTemplates(marketId, itemTemplateSet);
    }

    public int createItemLocation(MarketItemLocation itemLocation) {
        return itemLocationMapper.createItemLocation(itemLocation);
    }

    public MarketItemLocation getItemLocation(long itemId, String location) {
        return itemLocationMapper.getItemLocation(itemId, location);
    }

    public MarketItemLocation getItemLocationForMarket(long marketId, String location) {
        return itemLocationMapper.getItemLocationForMarket(marketId, location);
    }

    public List<MarketItemLocation> matchItemLocationForMarket(long marketId, String matchLocation, int pageStartIndex, int pageSize) {
        return itemLocationMapper.matchItemLocationForMarket(marketId, matchLocation, pageStartIndex, pageSize);
    }

    public List<MarketItemLocation> getItemLocations(long itemId) {
        return itemLocationMapper.getItemLocations(itemId);
    }

    public int offsetItemLocationStock(MarketItemLocation itemLocation, int decrementStock, int offsetType, long operatorPassportId, String operatorPassportName) {
        MarketItemLocationStockLogger itemLocationStockLogger = new MarketItemLocationStockLogger();

        itemLocationStockLogger.setItemId(itemLocation.getItemId());
        itemLocationStockLogger.setLocationCode(itemLocation.getLocationCode());
        itemLocationStockLogger.setBeforeStock(itemLocation.getStock());
        itemLocationStockLogger.setOffsetStock(-decrementStock);
        itemLocationStockLogger.setAfterStock(itemLocation.getStock() - decrementStock);
        itemLocationStockLogger.setOperationType(offsetType);
        itemLocationStockLogger.setOperatorPassportId(operatorPassportId);
        itemLocationStockLogger.setOperatorPassportName(operatorPassportName);

        createItemLocationStockLogger(itemLocationStockLogger);

        return itemLocationMapper.offsetItemLocationStock(itemLocation.getId(), decrementStock);
    }

    public int removeItemLocation(long id) {
        return itemLocationMapper.removeItemLocation(id);
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

    public List<MarketItemStockLockLogger> findInvalidItemStockLockLoggers(int status, String timeout) {
        return itemStockLockLoggerMapper.findInvalidItemStockLockLoggers(status, timeout);
    }

    public int releaseTimeoutItemLockStock(int status, String timeout, int matchStatus) {
        return itemStockLockLoggerMapper.releaseTimeoutItemLockStock(status, timeout, matchStatus);
    }

    public int modifyStockLockStatus(long id, int status) {
        return itemStockLockLoggerMapper.modifyStockLockStatus(id, status);
    }

    public int createPrepareAction(MarketPrepareAction prepareAction) {
        return prepareActionMapper.createPrepareAction(prepareAction);
    }

    public MarketPrepareAction getPrepareAction(long taskId) {
        return prepareActionMapper.getPrepareActionForId(taskId);
    }

    public MarketPrepareAction getPrepareAction(long marketId, String itemLocation, int status) {
        return prepareActionMapper.getPrepareAction(marketId, itemLocation, status);
    }

    public List<MarketPrepareAction> getPrepareActionsForLocationSet(long marketId, String locationSet, int status) {
        return prepareActionMapper.getPrepareActionsForLocationSet(marketId, locationSet, status);
    }

    public List<MarketPrepareAction> getPrepareActionForBarcode(long marketId, String barcode, int status) {
        return prepareActionMapper.getPrepareActionForBarcode(marketId, barcode, status);
    }

    public List<MarketPrepareAction> getUnCompletePrepareActions(long marketId, int pageStartIndex, int pageSize) {
        return prepareActionMapper.getPrepareActions(marketId, PrepareActionStatusEnum.UN_EXECUTOR.getKey(), pageStartIndex, pageSize);
    }

    public int modifyPrepareActionStatus(long marketId, String itemLocation, int matchStatus, int status, String time) {
        return prepareActionMapper.modifyPrepareActionStatus(marketId, itemLocation, matchStatus, status, time);
    }

    public List<String> loaderHotSearch(long marketId, int pageStartIndex, int pageSize) {
        return searchHistoryMapper.loaderHotSearch(marketId, pageStartIndex, pageSize);
    }

    public int incrementSearchTimes(long marketId, String searchKey) {
        return searchHistoryMapper.incrementSearchTimes(marketId, searchKey, CommonUtils.nowFormat());
    }

    public void createHistorySearch(long marketId, String searchKey) {
        MarketSearchHistory searchHistory = new MarketSearchHistory();

        searchHistory.setMarketId(marketId);
        searchHistory.setK(searchKey);
        searchHistoryMapper.createHistorySearch(searchHistory);
    }

    public List<MarketItemLadderPrice> loadItemLadderPrices(String itemSet) {
        return itemLadderPriceMapper.loadItemLadderPrices(itemSet);
    }

    private void createItemLocationStockLogger(MarketItemLocationStockLogger itemLocationStockLogger) {
        Runnable runnable = () -> itemLocationStockLoggerMapper.createLocationStockLogger(itemLocationStockLogger);
        AsyncScheduledService.submitImmediateSaveTask(runnable);
    }
}