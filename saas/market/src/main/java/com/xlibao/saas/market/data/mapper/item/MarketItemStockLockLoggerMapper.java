package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.saas.market.data.model.MarketItemStockLockLogger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketItemStockLockLoggerMapper {

    int createItemStockLogger(MarketItemStockLockLogger itemStockLockLogger);

    List<MarketItemStockLockLogger> getItemStockLockLoggers(@Param("orderSequenceNumber") String orderSequenceNumber, @Param("key") int key, @Param("status") int status);
}