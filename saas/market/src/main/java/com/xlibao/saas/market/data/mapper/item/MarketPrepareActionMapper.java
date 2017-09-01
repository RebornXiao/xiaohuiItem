package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.market.data.model.MarketPrepareAction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketPrepareActionMapper {

    int createPrepareAction(MarketPrepareAction prepareAction);

    MarketPrepareAction getPrepareAction(@Param("marketId") long marketId, @Param("itemLocation") String itemLocation, @Param("status") int status);

    List<MarketPrepareAction> getPrepareActions(@Param("marketId") long marketId, @Param("status") int status, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<MarketPrepareAction> getPrepareActionsForLocationSet(@Param("marketId") long marketId, @Param("locationSet") String locationSet, @Param("status") int status);

    List<MarketPrepareAction> getPrepareActionForBarcode(@Param("marketId") long marketId, @Param("barcode") String barcode, @Param("status") int status);

    int modifyPrepareActionStatus(@Param("marketId") long marketId, @Param("itemLocation") String itemLocation, @Param("matchStatus") int matchStatus, @Param("status") int status, @Param("time") String time);

    MarketPrepareAction getPrepareActionForId(@Param("taskId") long taskId);
}