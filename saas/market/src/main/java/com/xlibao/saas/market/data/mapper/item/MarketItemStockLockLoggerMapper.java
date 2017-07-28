package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.saas.market.data.model.MarketItemStockLockLogger;

import java.util.List;

public interface MarketItemStockLockLoggerMapper {

    int createItemStockLogger(MarketItemStockLockLogger itemStockLockLogger);

    List<MarketItemStockLockLogger> getItemStockLockLoggers(String orderSequenceNumber, int key, int status);
}